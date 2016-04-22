(ns small-star-empires.shared-tests
  (:require [clojure.test :refer :all]
            [small_star_empires.shared :as shared]))

(deftest assemble-test
  (testing "single tile"
    (is (= {[0 -1] [:planet-1],
            [-1 0] [:empty],
            [0 0]  [:homeworld],
            [-1 1] [:planet-1]}
           (shared/assemble [(shared/tile 0 0 shared/homeworld-tile)]))))

  (testing "single tile twice"
    (is (= {[0 -1] [:planet-1 :planet-1],
            [-1 0] [:empty :empty],
            [0 0]  [:homeworld :homeworld],
            [-1 1] [:planet-1 :planet-1]}
           (shared/assemble [(shared/tile 0 0 shared/homeworld-tile)
                             (shared/tile 0 0 shared/homeworld-tile)]))))

  (testing "single tile twice with rotation"
    (is (= {[4 6] [:planet-1 :planet-1],
            [3 7] [:empty],
            [4 7] [:homeworld :homeworld],
            [3 8] [:planet-1],
            [5 7] [:planet-1],
            [5 6] [:empty]}
           (shared/assemble [(shared/tile 4 7 shared/homeworld-tile)
                             (shared/tile 4 7 (-> shared/homeworld-tile
                                                  (shared/rotate-tile)
                                                  (shared/rotate-tile)))])))))