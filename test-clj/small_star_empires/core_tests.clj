(ns small-star-empires.core-tests
  (:require [clojure.test :refer :all]))

(deftest keep-next-test
  (testing "single usecase"
    (is (= [1 3] [1 3]))))
