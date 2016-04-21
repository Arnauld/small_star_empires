(ns small_star_empires.shared)


(def homeworld-tile
  [{:dx 0 :dy -1 :type :planet-1}
   {:dx -1 :dy 0 :type :empty} {:dx 0 :dy 0 :type :homeworld}
   {:dx -1 :dy 1 :type :planet-1}])

(def t1a-tile
  [{:dx 0 :dy -1 :type :nova-red} {:dx 1 :dy -1 :type :planet-1}
   {:dx -1 :dy 0 :type :planet-2} {:dx 0 :dy 0 :type :empty} {:dx 1 :dy 0 :type :planet-3}
   {:dx -1 :dy 1 :type :nova-green} {:dx 0 :dy 1 :type :planet-1}])

(def t2a-tile
  [{:dx 0 :dy -1 :type :nova-blue} {:dx 1 :dy -1 :type :planet-1}
   {:dx -1 :dy 0 :type :planet-1} {:dx 0 :dy 0 :type :empty} {:dx 1 :dy 0 :type :planet-2}
   {:dx -1 :dy 1 :type :planet-3} {:dx 0 :dy 1 :type :planet-1}])


(defn append
  [mm k v]
  (assoc mm k (conj (get mm k []) v)))

(defn project-tile-into [grid tile]
  (let [cx (:cx tile)
        cy (:cy tile)
        tile-defs (:defs tile)]
    (reduce (fn [g tile-def]
              (let [dx (:dx tile-def)
                    dy (:dy tile-def)
                    tp (:type tile-def)]
                (append g [(+ cx dx) (+ cy dy)] tp)))
            grid tile-defs)))

(defn assemble [tiles]
  (reduce (fn [acc tile]
            (project-tile-into acc tile)) {} tiles))
