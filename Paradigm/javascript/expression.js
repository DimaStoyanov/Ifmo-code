var binaryFunction = function (h) {
    return function (f, g) {
        return function (x, y, z) {
            return h(f(x, y, z), g(x, y, z))
        }
    }
};

var unaryFunction = function (h) {
    return function (f) {
        return function (x, y, z) {
            return h(f(x, y, z))
        }
    }
};

var cnst = function (arg) {
    return function () {
        return arg
    }
};

var variable = function (arg) {
    return function () {
        return arguments[["x", "y", "z"].indexOf(arg)]
    }
};

var add = binaryFunction(function (x, y) {
    return x + y
});

var subtract = binaryFunction(function (x, y) {
    return x - y
});

var multiply = binaryFunction(function (x, y) {
    return x * y
});

var divide = binaryFunction(function (x, y) {
    return x / y;
});

var mod = binaryFunction(function (x, y) {
    return x % y
});

var negate = unaryFunction(function (x) {
    return -x
});

var power = binaryFunction(Math.pow);

var abs = unaryFunction(Math.abs);

var log = unaryFunction(Math.log);

var isNumber = function (n) {
    return !isNaN(parseInt(n))
};


var parse = function (expression) {
    var tokens = expression.split(' ');
    var stack = [];
    var tokensLen = tokens.length;
    for (var i = 0; i < tokensLen; i++) {
        switch (tokens[i]) {
            case "+":
                var summand = stack.pop();
                stack.push(add(stack.pop(), summand));
                break;
            case "-":
                var minuend = stack.pop();
                stack.push(subtract(stack.pop(), minuend));
                break;
            case "*":
                var multiplier = stack.pop();
                stack.push(multiply(stack.pop(), multiplier));
                break;
            case "/":
                var denominator = stack.pop();
                stack.push(divide(stack.pop(), denominator));
                break;
            case "**":
                var exponent = stack.pop();
                stack.push(power(stack.pop(), exponent));
                break;
            case "log":
                stack.push(log(stack.pop()));
                break;
            case "abs":
                stack.push(abs(stack.pop()));
                break;
            case "%":
                var a = stack.pop();
                stack.push(mod(stack.pop(), a));
                break;
            case "negate":
                stack.push(negate(stack.pop()));
                break;
            case "":
                break;
            default:
                if (isNumber(tokens[i])) {
                    stack.push(cnst(parseInt(tokens[i])))
                } else {
                    stack.push(variable(tokens[i]))
                }
        }
    }
    return stack.pop()
};

