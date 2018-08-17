(ns cljthreetest1.core
  (:require [cljsjs.three]))

(def renderer-color 0xEEEEEE)
(def plane-color 0xCCCCCC)
(def cube-color 0xFF0000)
(def sphere-color 0x7777FF)

(defn create-renderer
  []
  ((fn [renderer]
     (do
;       (.setClearColorHex renderer)
       (.setClearColor renderer (js/THREE.Color. renderer-color))
       (.setSize renderer (.-innerWidth js/window) (.-innerHeight js/window))
       renderer))
   (js/THREE.WebGLRenderer.)))

(defn create-basic-stuff
  []
  {:scene (js/THREE.Scene.)
   :camera (js/THREE.PerspectiveCamera. 45 (/ (.-innerWidth js/window) (.-innerHeight js/window)) 0.1 1000)
   :renderer (create-renderer)})

(defn add-axes
  [{:keys [scene camera renderer] :as stuff}]
  (do
    (.add scene (js/THREE.AxisHelper. 20))
    stuff))

(defn create-sphere
  []
  (js/THREE.Mesh.
    (js/THREE.SphereGeometry. 4 20 20)
    (js/THREE.MeshBasicMaterial. (js-obj "color" sphere-color "wireframe" true))))

(defn position-object
  [object x y z]
  (do
    (set! (.-x (.-position object)) x)
    (set! (.-y (.-position object)) y)
    (set! (.-z (.-position object)) z)
    object))

(defn add-sphere
  [{:keys [scene camera renderer] :as stuff}]
  (do
    (.add scene (position-object (create-sphere) 20 4 2))
    stuff))

(defn create-plane
  []
  (js/THREE.Mesh.
    (js/THREE.PlaneGeometry. 60 20)
    (js/THREE.MeshBasicMaterial. (js-obj "color" plane-color))))

(defn rotate-object
  [object r]
  (do
    (set! (.-x (.-rotation object)) r)
    object))

(defn add-plane
  [{:keys [scene camera renderer] :as stuff}]
  (do
    (.add scene (rotate-object (position-object (create-plane) 15 0 0) (* -0.5 (.-PI js/Math))))
    stuff))

(defn create-cube
  []
  (js/THREE.Mesh.
    (js/THREE.BoxGeometry. 4 4 4)
    (js/THREE.MeshBasicMaterial. (js-obj "color" cube-color "wireframe" true))))

(defn add-cube
  [{:keys [scene camera renderer] :as stuff}]
  (do
    (.add scene (position-object (create-cube) -4 3 0))
    stuff))

(defn add-output-to-html
  [{:keys [scene camera renderer]}]
  (do
    (position-object camera -30 40 30)
    (.lookAt camera (.-position scene))
    (.appendChild (.getElementById js/document "webGL-output") (.-domElement renderer))
    (.render renderer scene camera)
    nil))

(defn ^:export init
  []
  (-> (create-basic-stuff)
      add-axes
      add-plane
      add-cube
      add-sphere
      add-output-to-html))
