(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'com.degel.re-frame.storage
   :output-to "out/re_frame_storage_fx.js"
   :output-dir "out"})
