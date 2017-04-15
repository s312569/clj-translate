(ns clj-translate.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defonce codon-tables
  (edn/read-string (slurp (io/resource "codon-table.clj"))))

(defonce iupacAminoAcids
  (edn/read-string (slurp (io/resource "aa-iupacAA.clj"))))

(defonce iupacNucleicAcids
  (edn/read-string (slurp (io/resource "dna-iupacdna.clj"))))

(defonce signalpAminoAcids
  (edn/read-string (slurp (io/resource "aa-signalp.clj"))))

(defn revcom
  "Takes a seq of chars representing nucleic acids and returns a
  vector of the reverse complement."
  [v]
  (->> (map #(or ((iupacNucleicAcids (Character/toUpperCase %)) :complement) \X) v)
       reverse
       vec))

(defn translate
  "Takes a string or a vector of characters representing nucleic acids
  and returns a translation as a vector of characters representing
  amino acids (single character code). Frame is specififed as an
  integer either 0, 1 or 2. Table is specified as an integer (default
  1; standard code) as follows:
  1 Standard
  2 Vertebrate Mitochondrial
  3 Yeast Mitochondrial
  4 Mold Mitochondrial; Protozoan Mitochondrial; Coelenterate
    Mitochondrial; Mycoplasma; Spiroplasma
  5 Invertebrate Mitochondrial
  6 Ciliate Nuclear; Dasycladacean Nuclear; Hexamita Nuclear
  9 Echinoderm Mitochondrial; Flatworm Mitochondrial
  10 Euplotid Nuclear
  11 Bacterial, Archaeal and Plant Plastid
  12 Alternative Yeast Nuclear
  13 Ascidian Mitochondrial
  14 Alternative Flatworm Mitochondrial
  15 Blepharisma Macronuclear
  16 Chlorophycean Mitochondrial
  21 Trematode Mitochondrial
  22 Scenedesmus obliquus Mitochondrial
  23 Thraustochytrium Mitochondrial
  24 Pterobranchia Mitochondrial"
  ([s frame] (translate s frame 1))
  ([s frame table]
   (let [v {\T 0 \U 0 \C 1 \A 2 \G 3}
         t (-> (codon-tables table) :ncbieaa)]
     (->> (partition-all 3 (subvec (vec s) frame))
          (mapv #(let [lst (map (fn [x] (Character/toUpperCase x)) %)]
                   (if (or (not (empty? (remove (set (keys v)) lst)))
                           (< (count lst) 3))
                     \X
                     (nth t (+ (* (v (first lst)) 16)
                               (* (v (second lst)) 4)
                               (v (nth lst 2)))))))))))
