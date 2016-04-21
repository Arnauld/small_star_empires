(ns small-star-empires.shared-tests
  (:require [clojure.test :refer :all]
            [small_star_empires.shared :as shared]))

(deftest assemble-test
  (testing "single tile"
    (is (= {[0 -1] [:planet-1],
            [-1 0] [:empty],
            [0 0] [:homeworld],
            [-1 1] [:planet-1]}
           (shared/assemble [{:cx 0 :cy 0 :defs shared/homeworld-tile}]))))
  (testing "single tile twice"
    (is (= {[0 -1] [:planet-1 :planet-1],
            [-1 0] [:empty :empty],
            [0 0]  [:homeworld :homeworld],
            [-1 1] [:planet-1 :planet-1]}
           (shared/assemble [{:cx 0 :cy 0 :defs shared/homeworld-tile}
                             {:cx 0 :cy 0 :defs shared/homeworld-tile}])))))