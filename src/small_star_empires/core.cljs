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

(def PI_D3 (/ Math/PI 3))
(def HEX (map (fn [i] [(Math/cos (* i PI_D3))
                       (Math/sin (* i PI_D3))])
              (range 0 6)))

(defn hex-points [x y radius]
  (map (fn [[hx hy]] [(+ x (* radius hx))
                      (+ y (* radius hy))])
       HEX))

(defn format-points [points]
  (reduce (fn [acc [x y]] (str acc " " (int x) "," (int y))) "" points))

(defn draw-hex [x y radius color]
  [:polygon {:style  {:fill            (rgb color)
                      :stroke          "black"
                      :stroke-width    "4px"
                      :stroke-linejoin "round"}
             :points (format-points (hex-points x y radius))}])

(defn board-view []
  (let [radius 40
        width (* 2 radius)
        height (* width (/ (Math/sqrt 3) 2))
        deltaX (* width (/ 3 4))
        deltaX2 (* 2 deltaX)
        deltaY (/ height 2)
        deltaY2 (* 2 deltaY)
        x 40
        y 40]
    [:svg {:style    {:border "1px solid black"
                      :width  400
                      :height 300}
           :view-box (string/join " " [0 0 400 400])}
     (draw-hex x y radius :pink)
     (draw-hex x (+ y deltaY2) radius :darkred)
     (draw-hex (+ x deltaX) (+ y deltaY) radius :orange)
     (draw-hex (+ x deltaX2) y radius :darkblue)
     (draw-hex (+ x deltaX2) (+ y deltaY2) radius :darkred)
     (draw-hex (+ x deltaX2) (+ y deltaY2 deltaY2) radius :darkblue)
     (draw-hex (+ x deltaX2 deltaX2) y radius :pink)
     (draw-hex (+ x deltaX2 deltaX2 deltaX2) y radius :pink)
     ]))

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
