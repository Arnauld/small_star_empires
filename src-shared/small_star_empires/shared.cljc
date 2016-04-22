(ns small_star_empires.shared)

(defn axial-to-cube [[axial-x axial-y]]
  (let [x axial-x
        z axial-y
        y (* -1 (+ x z))]
    [x y z]))

(defn cube-to-axial [[x _ z]]
  [x z])

(defn negate [x] (* -1 x))

(defn rotate-cube-coords [[x y z]]
  [(negate z) (negate x) (negate y)])

(defn rotate-tile [tile]
  (map (fn [tile-def]
         (let [axial-x (:dx tile-def)
               axial-y (:dy tile-def)
               [nx ny] (-> [axial-x axial-y]
                           (axial-to-cube)
                           (rotate-cube-coords)
                           (cube-to-axial))]
           (assoc tile-def :dx nx :dy ny)))
       tile))

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

(defn tile [axial-x axial-y cell-defs]
  {:cx    axial-x
   :cy    axial-y
   :cells cell-defs})

(defn multimap-put
  [mm k v]
  (assoc mm k (conj (get mm k []) v)))

(defn project-tile-into [grid tile]
  (let [cx (:cx tile)
        cy (:cy tile)
        cell-defs (:cells tile)]
    (reduce (fn [g tile-def]
              (let [dx (:dx tile-def)
                    dy (:dy tile-def)
                    tp (:type tile-def)]
                (multimap-put g [(+ cx dx) (+ cy dy)] tp)))
            grid cell-defs)))

(defn assemble [tiles]
  (reduce (fn [acc tile]
            (project-tile-into acc tile)) {} tiles))
