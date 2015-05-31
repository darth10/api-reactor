(ns api-reactor.test.common
  (:require [api-reactor.main :as main]
            [clojure.data.json :refer [read-str]]))

(def test-app main/app)

;;; TODO user cheshire lib instead
(defn read-json [str] (read-str str :key-fn keyword))
