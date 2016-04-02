(ns small_star_empires.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [clojure.string :as string]))


(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Hello world!"}))

(defn rgb [color]
  (case color
    :pink "#ff69b4"
    :orange "#ffa500"
    :darkblue "#000080"
    :darkred "#8b0000"
    :black "#000"
    :white "#fff"
    :grey "#777"
    "#181818"))

(defn block [x y color]
  [:rect {:x            x
          :y            y
          :width        1
          :height       1
          :stroke       "black"
          :stroke-width 0.01
          :rx           0.1
          :fill         (rgb color)}])

(def TWO_PI (* 2 Math/PI))
(def PI_D3 (/ Math/PI 3))
(def HEX (map (fn [i] [(Math/cos (* i PI_D3))
                       (Math/sin (* i PI_D3))])
              (range 0 6)))

(defn hex-points [x y radius]
  (map (fn [[hx hy]] [(+ x (* radius hx))
                      (+ y (* radius hy))])
       HEX))

(defn format-points [points]
  (reduce (fn [acc [x y]] (str acc " " x "," y)) "" points))

(defn fill-hex
  ([x y radius color]
   (fill-hex x y radius color 0))
  ([x y radius color rounded-radius]
   (let [radiusToUse (- radius (* 0.75 rounded-radius))]
     [:polygon {:style  {:fill            (rgb color)
                         :stroke          (rgb color)
                         :stroke-width    (str rounded-radius "px")
                         :stroke-linejoin "round"}
                :points (format-points (hex-points x y radiusToUse))}])))

(defn draw-hex
  ([x y radius color]
   (draw-hex x y radius color 2))
  ([x y radius color rounded-radius]
   (let [radiusToUse (- radius (* 0.75 rounded-radius))]
     [:polygon {:style  {:fill            "none"
                         :stroke          (rgb color)
                         :stroke-width    (str rounded-radius "px")
                         :stroke-linejoin "round"}
                :points (format-points (hex-points x y radiusToUse))}])))

(defn fill-circle
  ([x y radius color]
   (fill-circle x y radius color 2))
  ([x y radius color rounded-radius]
   [:circle {:style {:fill            (rgb color)
                     :stroke          (rgb color)
                     :stroke-width    (str rounded-radius "px")
                     :stroke-linejoin "round"}
             :cx    x
             :cy    y
             :r     radius}]))

(defn draw-circle
  ([x y radius color]
   (draw-circle x y radius color 2))
  ([x y radius color rounded-radius]
   [:circle {:style {:fill            "none"
                     :stroke          (rgb color)
                     :stroke-width    (str rounded-radius "px")
                     :stroke-linejoin "round"}
             :cx    x
             :cy    y
             :r     radius}]))


(defn draw-planet-orbit [x y orbit-radius orbit-angle]
  [(draw-circle x y orbit-radius :grey 1)
   (fill-circle (+ x (* orbit-radius (Math/cos orbit-angle)))
                (+ y (* orbit-radius (Math/sin orbit-angle))) 4 :black)
   (fill-circle (+ x (* orbit-radius (Math/cos orbit-angle)))
                (+ y (* orbit-radius (Math/sin orbit-angle))) 2 :white)])

(defn draw-planet [x y]
  (-> [(fill-hex x y 40 :black)
       (draw-hex x y 35 :grey)
       (draw-circle x y 15 :grey 1)
       (fill-circle x y 1 :white)]
      (into (draw-planet-orbit x y 15 PI_D3))))

(defn draw-planet-2 [x y]
  (let [ANG1 (/ TWO_PI 7)
        ANG2 (/ TWO_PI 2)
        ANG3 (* TWO_PI 0.8)]
    (-> [(fill-hex x y 40 :black)
         (draw-hex x y 35 :grey)
         (fill-circle x y 1 :white)]
        (into (draw-planet-orbit x y 10 ANG2))
        (into (draw-planet-orbit x y 17 ANG3))
        (into (draw-planet-orbit x y 24 ANG1)))))

(defn board-view []
  (let [radius 40
        width (* 2 radius)
        height (* width (/ (Math/sqrt 3) 2))
        deltaX (* width (/ 3 4))
        deltaX2 (* 2 deltaX)
        deltaY (/ height 2)
        deltaY2 (* 2 deltaY)
        x 40
        y 40
        root [:svg {:style    {:border "1px solid black"
                               :width  400
                               :height 300}
                    :view-box (string/join " " [0 0 400 400])}]]
    (-> root
        (into
          [(fill-hex x y radius :pink)
           (fill-hex x (+ y deltaY2) radius :darkred)
           (fill-hex (+ x deltaX) (+ y deltaY) radius :orange)
           (fill-hex (+ x deltaX2) y radius :darkblue)
           (fill-hex (+ x deltaX2) (+ y deltaY2) radius :darkred)
           (fill-hex (+ x deltaX2) (+ y deltaY2 deltaY2) radius :darkblue)
           (fill-hex (+ x deltaX2 deltaX2) y radius :pink)
           (fill-hex (+ x deltaX2 deltaX2 deltaX2) y radius :pink)])
        (into
          (draw-planet
            (+ x deltaX2 deltaX2) (+ y deltaY2)))
        (into
          (draw-planet-2
            (+ x deltaX2 deltaX2 deltaX) (+ y deltaY2 deltaY))))))

(defn hello-world []
  [:h1 (:text @app-state)
   [:br]
   [board-view]])

(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  (swap! app-state update-in [:__figwheel_counter] inc)
  )
