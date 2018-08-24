(ns cljthreetest1.core
  (:require [cljsjs.three]))

(def renderer-color 0xEEEEEE)
(def plane-color 0xCCCCCC)
(def cube-color 0xFF0000)
(def sphere-color 0x7777FF)

(defn create-renderer!
  []
  ((fn [renderer]
     (do
;       (.setClearColorHex renderer)
       (.setClearColor renderer (js/THREE.Color. renderer-color))
       (.setSize renderer (.-innerWidth js/window) (.-innerHeight js/window))
       renderer))
   (js/THREE.WebGLRenderer.)))

(defn create-scene!
  []
  (js/THREE.Scene.))

(defn create-camera!
  []
  (js/THREE.PerspectiveCamera. 45 (/ (.-innerWidth js/window) (.-innerHeight js/window)) 0.1 1000))

(defn create-sphere!
  []
  (js/THREE.Mesh.
    (js/THREE.SphereGeometry. 4 20 20)
    (js/THREE.MeshBasicMaterial. (js-obj "color" sphere-color "wireframe" true))))

(defn position-object!
  [object x y z]
  (do
    (set! (.-x (.-position object)) x)
    (set! (.-y (.-position object)) y)
    (set! (.-z (.-position object)) z)
    object))

(defn create-plane!
  []
  (js/THREE.Mesh.
    (js/THREE.PlaneGeometry. 60 20)
    (js/THREE.MeshBasicMaterial. (js-obj "color" plane-color))))

(defn rotate-object!
  [object r]
  (do
    (set! (.-x (.-rotation object)) r)
    object))

(defn create-cube!
  [edgesize]
  (js/THREE.Mesh.
    (js/THREE.BoxGeometry. edgesize edgesize edgesize)
    (js/THREE.MeshBasicMaterial. (js-obj "color" cube-color "wireframe" true))))

(defn add-output-to-html!
  [scene camera renderer]
  (do
    (.lookAt camera (.-position scene))
    (.appendChild (.getElementById js/document "webGL-output") (.-domElement renderer))
    (.render renderer scene camera)
    nil))

(defn populate-scene!
  [scene]
  (do
    (.add scene (js/THREE.AxisHelper. 20))
    (.add scene (rotate-object! (position-object! (create-plane!) 15 0 0) (* -0.5 (.-PI js/Math))))
    (.add scene (position-object! (create-sphere!) 20 4 2))
    (.add scene (position-object! (create-cube! 4) -4 3 0))
    scene))

(defn ^:export init
  []
  (add-output-to-html! (populate-scene! (create-scene!))
                       (position-object! (create-camera!) -30 40 30)
                       (create-renderer!)))
