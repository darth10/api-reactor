(ns api-reactor.test.middleware
  (:require [api-reactor.test.middleware :refer :all]
            [midje.sweet :refer :all]))

;; (let [resp (common/test-app
;;             {:request-method :get
;;              :headers {}
;;              :uri "/table"})]
;;   (:status resp)) => 200
;; (provided
;;   (handle-api-get-list ..db.. ..table..) => [{:id 1 :table_col 1}])
