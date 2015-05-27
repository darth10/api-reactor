(ns api-reactor.app-test
  (:use clojure.test)
  (:require [api-reactor.test-common :as common]))

(deftest test-landing-page
  (let [resp (common/test-app {:request-method :get
                               :uri "/"})]
    (is (= 200 (:status resp)))))

(deftest test-table-url
  (let [resp (common/test-app {:request-method :get
                             :uri "/table"})]
    (is (= 200 (:status resp)))))
