(ns api-reactor.test.main
  (:require [api-reactor.test.common :as common]
            [midje.sweet :refer :all]))

(fact
  (let [resp (common/test-app
              {:request-method :get
               :headers {}
               :uri "/"})]
    (:status resp)) => 404)
