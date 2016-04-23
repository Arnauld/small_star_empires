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
    (is (= {[11 1] [:planet-1 :planet-1],
            [10 2] [:empty],
            [11 2] [:homeworld :homeworld],
            [10 3] [:planet-1],
            [12 2] [:planet-1],
            [12 1] [:empty]}
           (shared/assemble [(shared/tile 11 2 shared/homeworld-tile)
                             (shared/tile 11 2 (-> shared/homeworld-tile
                                                   (shared/rotate-tile)
                                                   (shared/rotate-tile)))])))))

(deftest cells-in-error-test
  (testing "once assembled multiple cells at the same coordinate"
    (is (= {[11 1] [:planet-1 :planet-1],
            [11 2] [:homeworld :homeworld]}
           (shared/cells-in-error {[11 1] [:planet-1 :planet-1],
                                   [10 2] [:empty],
                                   [11 2] [:homeworld :homeworld],
                                   [10 3] [:planet-1],
                                   [12 2] [:planet-1],
                                   [12 1] [:empty]})))))