(ns small_star_empires.core
  (:require
    [reagent.core :as reagent :refer [atom]]
    [clojure.string :as string]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "Small Star Empires!"}))

(defn rgb [color]
  (case color
    :pink "#ff69b4"
    :orange "#ffa500"
    :darkblue "#000080"
    :darkred "#8b0000"
    :black "#000"
    :white "#fff"
    :grey "#777"
    :nova-red "#f22"
    :nova-blue "#0af"
    :nova-green "#0fa"
    :homeworld-green "#0f0"
    :red "#f00"
    "#181818"))

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

(defn hex
  ([x y radius color]
   (hex x y radius color {}))
  ([x y radius color styles]
   (let [rounded-radius (get styles :rounded-radius 0)
         radiusToUse (- radius (* 0.75 rounded-radius))
         default-styles {:fill            (rgb color)
                         :stroke          (rgb color)
                         :stroke-width    (str rounded-radius "px")
                         :stroke-linejoin "round"}]

     [:polygon {:style  (merge default-styles styles)
                :points (format-points (hex-points x y radiusToUse))}])))


(defn circle
  ([x y radius color]
   (circle x y radius color {}))
  ([x y radius color styles]
   (let [default-styles {:fill         (rgb color)
                         :stroke       (rgb color)
                         :stroke-width 2}]
     [:circle {:style (merge default-styles styles)
               :cx    x
               :cy    y
               :r     radius}])))

(defn draw-planet-orbit
  ([x y orbit-radius orbit-angle]
   (draw-planet-orbit x y orbit-radius orbit-angle :grey :white))
  ([x y orbit-radius orbit-angle orbit-color planet-color]
   [:g {:class "orbit"}
    (circle x y orbit-radius orbit-color {:fill         "none"
                                          :stroke-width 1})
    (circle (+ x (* orbit-radius (Math/cos orbit-angle)))
            (+ y (* orbit-radius (Math/sin orbit-angle))) 4 :black)
    (circle (+ x (* orbit-radius (Math/cos orbit-angle)))
            (+ y (* orbit-radius (Math/sin orbit-angle))) 2 planet-color)]))

(defn draw-homeworld [x y]
  (let [homeworld-color :homeworld-green
        ANG1 (* TWO_PI 0.66)
        ANG2 (* TWO_PI 0.12)]
    [:g
     (hex x y 40 :black)
     (hex x y 35 homeworld-color {:fill             "none"
                                  :rounded-radius   2
                                  :stroke-dasharray "7,7"})
     (circle x y 15 :grey {:fill         "none"
                           :stroke-width 1})
     (circle x y 1 homeworld-color)
     (draw-planet-orbit x y 15 ANG1 homeworld-color homeworld-color)
     (draw-planet-orbit x y 22 ANG2 homeworld-color homeworld-color)]))

(defn draw-empty-space [x y]
  [:g {:class "planet"}
   (hex x y 40 :black)
   (hex x y 35 :grey {:fill           "none"
                      :rounded-radius 2})])

(defn draw-planet-1 [x y]
  (let [ANG1 PI_D3]
    [:g {:class "planet"}
     (hex x y 40 :black)
     (hex x y 35 :grey {:fill           "none"
                        :rounded-radius 2})
     (circle x y 15 :grey {:fill         "none"
                           :stroke-width 1})
     (circle x y 1 :white)
     (draw-planet-orbit x y 15 ANG1)]))

(defn draw-planet-2 [x y]
  (let [ANG2 (/ TWO_PI 2)
        ANG3 (* TWO_PI 0.8)]
    [:g {:class "planet2"}
     (hex x y 40 :black)
     (hex x y 35 :grey {:fill           "none"
                        :rounded-radius 2})
     (circle x y 1 :white)
     (draw-planet-orbit x y 15 ANG2)
     (draw-planet-orbit x y 22 ANG3)]))

(defn draw-planet-3 [x y]
  (let [ANG1 (/ TWO_PI 7)
        ANG2 (/ TWO_PI 2)
        ANG3 (* TWO_PI 0.8)]
    [:g {:class "planet3"}
     (hex x y 40 :black)
     (hex x y 35 :grey {:fill           "none"
                        :rounded-radius 2})
     (circle x y 1 :white)
     (draw-planet-orbit x y 10 ANG2)
     (draw-planet-orbit x y 17 ANG3)
     (draw-planet-orbit x y 24 ANG1)]))

(defn draw-nova [x y nova-color]
  [:g {:class "nova"}
   (hex x y 40 :black)
   (hex x y 35 :grey {:fill           "none"
                      :rounded-radius 2})
   (circle x y 10 nova-color {:filter "url(#blur1)"})
   (circle x y 5 :white {:filter "url(#blur4"})
   (circle x (- y 13) 10 nova-color {:filter "url(#blur1"})
   (circle x (- y 13) 5 :white {:filter "url(#blur4"})
   (circle (+ x 15) (- y 5) 8 nova-color {:filter "url(#blur2"})
   (circle (- x 15) (- y 12) 8 nova-color {:filter "url(#blur2"})
   (circle (- x 15) (- y 12) 4 :white {:filter "url(#blur4"})
   (circle (- x 10) (- y 7) 4 :white {:filter "url(#blur4"})
   (circle (- x 2) (+ y 15) 10 nova-color {:filter "url(#blur3"})
   (circle (- x 2) (+ y 15) 4 :white {:filter "url(#blur4"})])

(def default-svg-filters
  [[:defs [:filter {:id     "blur1"
                    :x      -5
                    :y      -5
                    :height 20
                    :width  20}
           ["feGaussianBlur" {:in           "SourceGraphic"
                              :stdDeviation "10"}]]]
   [:defs [:filter {:id     "blur2"
                    :x      -5
                    :y      -5
                    :height 15
                    :width  15}
           ["feGaussianBlur" {:in           "SourceGraphic"
                              :stdDeviation "7"}]]]
   [:defs [:filter {:id     "blur3"
                    :x      -5
                    :y      -5
                    :height 15
                    :width  15}
           ["feGaussianBlur" {:in           "SourceGraphic"
                              :stdDeviation "6"}]]]
   [:defs [:filter {:id     "blur4"
                    :x      -5
                    :y      -5
                    :height 15
                    :width  15}
           ["feGaussianBlur" {:in           "SourceGraphic"
                              :stdDeviation "3"}]]]])

;
;

(def homeworld-tile
  [[:empty :planet-2]
   [:planet-1 :homeworld]])

(defn render-tile [tile-def x y deltaX deltaY]
  (let [tile-width (count tile-def)
        tile-height (count (first tile-def))
        offsetY (if (even? x) 0 1)]
    (into [:g {:class "tile"}]
          (for [i (range tile-width)
                j (range tile-height)]
            (let [col (+ x i)
                  row (+ y j (if (= (mod col 2) (mod x 2)) offsetY 0))
                  rx col
                  rz (- row (if (even? col) 0 0.5))
                  ;                  ry (+ rx rz)
                  tx (* rx deltaX)
                  ty (* rz (+ deltaY deltaY))
                  ]
              (case (get-in tile-def [j i])
                :empty (draw-empty-space tx ty)
                :planet-1 (draw-planet-1 tx ty)
                :planet-2 (draw-planet-2 tx ty)
                :planet-3 (draw-planet-3 tx ty)
                :homeworld (draw-homeworld tx ty)
                (hex tx ty 40 :red)))))))

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
        (into default-svg-filters)
        (into
          [(hex x y radius :pink)
           (hex x (+ y deltaY2) radius :darkred)
           (hex (+ x deltaX) (+ y deltaY) radius :orange)
           (hex (+ x deltaX2) y radius :darkblue)
           (hex (+ x deltaX2) (+ y deltaY2) radius :darkred)
           (hex (+ x deltaX2) (+ y deltaY2 deltaY2) radius :darkblue)
           (hex (+ x deltaX2 deltaX2) y radius :pink)
           (hex (+ x deltaX2 deltaX2 deltaX2) y radius :pink)])
        (into
          [(render-tile homeworld-tile 0 1 deltaX deltaY)
           (render-tile homeworld-tile 2 1 deltaX deltaY)
           (render-tile homeworld-tile 1 4 deltaX deltaY)
           (draw-planet-1
             (+ x deltaX2 deltaX2) (+ y deltaY2))
           (draw-homeworld
             (+ x deltaX2 deltaX2 deltaX) (+ y deltaY))
           (draw-planet-2
             (+ x deltaX2 deltaX2) (+ y deltaY2 deltaY2))
           (draw-planet-3
             (+ x deltaX2 deltaX2 deltaX) (+ y deltaY2 deltaY))
           (draw-nova
             (+ x deltaX2 deltaX2) (+ y deltaY2 deltaY2 deltaY2) :nova-red)
           (draw-nova
             (+ x deltaX2 deltaX2 deltaX) (+ y deltaY2 deltaY2 deltaY2 deltaY) :nova-blue)
           (draw-nova
             (+ x deltaX2 deltaX2 deltaX) (+ y deltaY2 deltaY2 deltaY) :nova-green)])
        )))

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
