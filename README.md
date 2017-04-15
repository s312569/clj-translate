# clj-translate

DNA and RNA translation library.

## Install

Include in project file:

[clj-translate "0.1.0"]

Include in namespace:

(:require [clj-translate.core :as tr])

## Usage

Pre-defines codon tables hash.

```clojure
clj-translate.core> codon-tables
{1 {:name "Standard", :ncbieaa [\F \F \L \L \S \S \S \S \Y \Y \* \* \C \C \* \W \L
\L \L \L \P \P \P \P \H \H \Q \Q \R \R \R \R \I \I \I \M \T \T \T \T \N \N \K \K
\S \S \R \R \V \V \V \V \A \A \A \A \D \D \E \E \G \G \G \G], :sncbieaa [\- \- \-
\M \- \- \- \- \- \- \- \- \- \- \- \- \- \- \- \M \- \- \- \- \- \- \- \- \- \-
\- \- \- \- \- \M \- \- \- \- \- \- \- \- \- \- \- \- \- \- \- \- \- \- \- \- \-
\- \- \- \- \- \- \-]}, 24 {:name "Pterobranchia Mitochondrial", ...
clj-translate.core> (dorun (map (fn [[k v]]
                                  (println k (:name v)))
                                codon-tables))
1 Standard
24 Pterobranchia Mitochondrial
4 Mold Mitochondrial; Protozoan Mitochondrial; Coelenterate Mitochondrial; Mycoplasma; Spiroplasma
15 Blepharisma Macronuclear
21 Trematode Mitochondrial
13 Ascidian Mitochondrial
22 Scenedesmus obliquus Mitochondrial
6 Ciliate Nuclear; Dasycladacean Nuclear; Hexamita Nuclear
3 Yeast Mitochondrial
12 Alternative Yeast Nuclear
2 Vertebrate Mitochondrial
23 Thraustochytrium Mitochondrial
11 Bacterial, Archaeal and Plant Plastid
9 Echinoderm Mitochondrial; Flatworm Mitochondrial
5 Invertebrate Mitochondrial
14 Alternative Flatworm Mitochondrial
16 Chlorophycean Mitochondrial
10 Euplotid Nuclear
nil
clj-translate.core>
```

Also pre-defines alphabets. At the moment iupacAminoAcids,
iupacNucleicAcids and signalpAminoAcids. For example:

```clojure
clj-translate.core> iupacNucleicAcids
{\A {:longname "Adenine", :complement \T}, \B {:longname "C, T, U, or G (not A)",
:complement \V}, \C {:longname "Cytosine", :complement \G}, \D {:longname
"A, T, U, or G (not C)", :complement \H}, \G {:longname "Guanine", :complement \C},
\H {:longname "A, T, U, or C (not G)", :complement \D}, \K {:longname "T, U, or G",
:complement \M}, \- {:longname "Gap (dash)", :complement \-}, \M {:longname "C or A",
:complement \K}, \. {:longname "Gap (dot)", :complement \.}, \N {:longname
"Any base (A, C, G, T, or U)", :complement \N}, \R {:longname "Purine (A or G)",
:complement \Y}, \S {:longname "C or G", :complement \S}, \T {:longname "Thymine",
:complement \A}, \U {:longname "Uracil", :complement \A}, \V {:longname
"A, C, or G (not T, not U)", :complement \B}, \W {:longname "T, U, or A",
:complement \W}, \X {:longname "Any base (A, C, G, T, or U)", :complement \X},
\Y {:longname "Pyrimidine (C, T, or U)", :complement \R}}
clj-translate.core> 
```

Defined functions are `revcom` and `translate`. `revcom` takes a
collection of characters representing nucleic acids and returns the
reverse complement of the collection. `translate` takes a string or a
vector of characters representing a nucleic acid sequence, a frame
(integer values 0, 1 or 2) and an optional integer table code
corresponding to the keys of the predefine `codon-tables` described
above. `translate` returns a vector of amino acids in single character
code:

```clojure
clj-translate.core> (revcom [\a \g \c \t \c \g \c \t \a \c])
[\G \T \A \G \C \G \A \G \C \T]
clj-translate.core> (translate [\a \g \c \t \c \g \c \t \a \c] 0)
[\S \S \L \X]
clj-translate.core> 
```

## License

Copyright © 2017 Jason Mulvenna

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
