; Functions
(defn constant [value]
  (fn [arguments] value))

(defn variable [name]
  (fn [arguments] (arguments name)))

(defn operation [h]
  (fn [& operands]
    (fn [arguments]
      (apply h (map (fn [x] (x arguments)) operands)))))

(defn unaryOperation [h]
  (fn [operand]
    (fn [arguments]
      (h (operand arguments)))))




(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(defn divide [a b] (fn [args] (/ (double (a args)) (double (b args)))))

(def cos (unaryOperation (fn [x] (Math/cos x))))
(def sin (unaryOperation (fn [x] (Math/sin x))))
(def negate (unaryOperation -))


(def funcOperation {'+  add, '- subtract, '* multiply, '/ divide,
                    'negate negate, 'cos cos, 'sin sin})

; Parser
(defn parseFunction [expression]
  ((fn parseList [element]
     (cond
       (symbol? element) (variable (str element))
       (number? element) (constant element)
       :else (apply (funcOperation (first element)) (map parseList (rest element))))
     ) (read-string expression)))

