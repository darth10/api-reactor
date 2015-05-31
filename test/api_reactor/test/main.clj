(ns api-reactor.test.main
  (:use midje.sweet)
  (:require [api-reactor.test.common :as common]))

(fact
 (let [resp (common/test-app
             {:request-method :get
              :uri "/"})]
   (:status resp)) => 200

 (let [resp (common/test-app
             {:request-method :get
              :uri "/table"})]
   (:status resp)) => 200)
