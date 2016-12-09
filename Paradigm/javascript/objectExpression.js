function operation(evalFunction, diffFunction, nameOfOperate, numberOfArguments) {
    function Constructor() {
        this.args = toArray(arguments)
    }

    Constructor.prototype.toString = function () {
        return this.args.map(getString).join(" ") + " " + nameOfOperate
    };
    Constructor.prototype.postfix = function () {
        return "(" + this.args.map(getPostfix).join(" ") + " " + nameOfOperate + ")";
    };
    Constructor.prototype.prefix = function () {
        return "(" + nameOfOperate + " " + this.args.map(getPrefix).join(" ") + ")"
    };
    Constructor.prototype.evaluate = function (x, y, z) {
        return evalFunction.apply(null, this.args.map(getValue(arguments)))
    };
    Constructor.prototype.diff = function (arg) {
        return diffFunction(this, arg)
    };
    Constructor.prototype.numberOfArguments = numberOfArguments;
    operators[nameOfOperate] = Constructor;
    return Constructor;
}

function toArray(args) {
    var r = [];
    r.push.apply(r, args);
    return r;
}
function getValue(args) {
    return function (expression) {
        return expression.evaluate.apply(expression, args);
    }
}
function getString(expression) {
    return expression.toString();
}
function getPrefix(expression) {
    return expression.prefix();
}
function getPostfix(expression) {
    return expression.postfix();
}

function Const(arg) {
    this.cnst = arg
}
Const.prototype.toString = function () {
    return this.cnst.toString()
};
Const.prototype.postfix = function () {
    return this.cnst.toString();
};
Const.prototype.prefix = function () {
    return this.cnst.toString();
};
Const.prototype.evaluate = function () {
    return this.cnst
};
Const.prototype.diff = function () {
    return new Const(0)
};

var vars = ["x", "y", "z"];

function Variable(arg) {
    this.v = arg
}
Variable.prototype.toString = function () {
    return this.v
};
Variable.prototype.postfix = function () {
    return this.v
};
Variable.prototype.prefix = function () {
    return this.v;
};
Variable.prototype.evaluate = function () {
    return arguments[vars.indexOf(this.v)]
};
Variable.prototype.diff = function (arg) {
    return new Const(arg == this.v ? 1 : 0)
};

var operators = [];

var Add = operation(function (x, y) {
    return x + y
}, function (operands, arg) {
    return new Add(operands.args[0].diff(arg), operands.args[1].diff(arg))
}, "+", 2);

var Subtract = operation(function (x, y) {
    return x - y
}, function (operands, arg) {
    return new Subtract(operands.args[0].diff(arg), operands.args[1].diff(arg))
}, "-", 2);

var Multiply = operation(function (x, y) {
    return x * y
}, function (operands, arg) {
    return new Add(new Multiply(operands.args[0].diff(arg), operands.args[1]), new Multiply(operands.args[0], operands.args[1].diff(arg)))
}, "*", 2);

var Divide = operation(function (x, y) {
    return x / y
}, function (operands, arg) {
    return new Divide(new Subtract(new Multiply(operands.args[0].diff(arg), operands.args[1]), new Multiply(operands.args[0], operands.args[1].diff(arg))),
        new Multiply(operands.args[1], operands.args[1]))
}, "/", 2);

var Negate = operation(function (x) {
    return -x
}, function (operand, arg) {
    return new Negate(operand.args[0].diff(arg))
}, "negate", 1);

var Sin = operation(Math.sin, function (operand, arg) {
    return new Multiply(new Cos(operand.args[0]), operand.args[0].diff(arg))
}, "sin", 1);

var Cos = operation(Math.cos, function (operand, arg) {
    return new Multiply(new Negate(new Sin(operand.args[0])), operand.args[0].diff(arg))
}, "cos", 1);

var Exp = operation(Math.exp, function (operand, arg) {
    return new Multiply(new Exp(operand.args[0]), operand.args[0].diff(arg))
}, "exp", 1);

var ArcTan = operation(Math.atan, function (operand, arg) {
    return new Divide(operand.args[0].diff(arg), new Add(new Const(1), new Multiply(operand.args[0], operand.args[0])))
}, "atan", 1);


var parse = function (expression) {
    var tokens = expression.split(' ');
    var stack = [];
    var tokensLen = tokens.length;
    for (var i = 0; i < tokensLen; i++) {
        if (operators[tokens[i]] != undefined) {
            var f = operators[tokens[i]];
            var n = f.prototype.numberOfArguments;
            var expr = Object.create(f.prototype);
            var args = stack.splice(stack.length - n, n);
            f.apply(expr, args);
            stack.push(expr);
        } else if (tokens[i] == "") {
        } else if (isNumber(tokens[i])) {
            stack.push(new Const(parseInt(tokens[i])));
        } else {
            stack.push(new Variable(tokens[i]));
        }
    }
    return stack[0];

    function isNumber(n) {
        return !isNaN(parseInt(n));
    }
};


function ParserError(message) {
    this.name = "ParserError";
    this.message = message;
}
ParserError.prototype = Error.prototype;

function parseExpression(expression, bracketParser, stackOrder) {
    var exprLen = expression.length, tokensSize = [];
    var tokens = parseToken();
    var token, tokensLen = tokens.length;
    var expr, index = 0, typeParser = bracketParser('(') == '(';
    expr = parseOperand();
    if (expr == undefined)
        throw new ParserError("Empty input");
    if (expr == ')')
        throw new ParserError("Expression starts with close bracket");
    if (index < tokensLen)
        throw new ParserError("Excessive info at index " + getIndex(index));
    return expr;


    function parseToken() {
        var tokens = [];
        var index = 0, token;
        var separator = " ()";
        while ((token = getToken()) != undefined) {
            tokens.push(token);
            tokensSize.push(index);
        }
        tokensSize = stackOrder(tokensSize);
        return stackOrder(tokens);

        function getToken() {
            while (expression[index] == " ")
                index++;
            if (index == exprLen)
                return undefined;
            if (expression[index] == '(' || expression[index] == ')') {
                return bracketParser(expression[index++]);
            }
            var startIndex = index;
            while (index < exprLen && separator.indexOf(expression[index]) == -1)
                index++;
            return expression.substring(startIndex, index);
        }
    }


    function parseOperand() {
        if (index == tokensLen)
            return undefined;
        token = tokens[index++];
        if (token == '(')
            return parseOperate();
        if (token == ')')
            return ')';
        if (isAlpha(token)) {
            if (vars.indexOf(token) == -1)
                throw new ParserError("Unknown variable: [->" + token + "<-] at index " +
                    (getIndex(index - 1) - 1));
            return new Variable(token);
        }
        if (operators[token] != undefined)
            throw new ParserError("Missing " + (bracketParser("(") == '(' ? "open" : "close") + " bracket");
        return new Const(getNumber(token));
    }


    function parseOperate() {
        var stack = [], operand;
        var operate = tokens[index++];
        if (operators[operate] == undefined)
            throw new ParserError("Invalid operator: [->" + operate + "<-] at index " + (getIndex(index - 1) - 1));
        while ((operand = parseOperand()) != ')') {
            if (operand == undefined)
                throw new ParserError("Missing close brackets");
            stack.push(operand);
        }
        var f = operators[operate], n = f.prototype.numberOfArguments;
        if (stack.length != n) {
            var i = getIndex(index - stack.length);
            throw new ParserError((stack.length < n ? "Not enough" : "Too many") + " arguments for operation: [->" + operate + "<-] " +
                "at index " + (getIndex(index - stack.length) + 1)
                + "\nNeed " + n + (n > 1 ? " arguments" : " argument") + ", has " + stack.length);
        }
        var curExpr = Object.create(f.prototype);
        f.apply(curExpr, stackOrder(stack));
        return curExpr;
    }


    function isAlpha(str) {
        return /^[A-Za-z]*$/.test(str);
    }


    function getNumber(s) {
        var numbers = "0123456789";
        var l = s.length;
        for (var i = s[0] == "-" ? 1 : 0; i < l; i++) {
            if (numbers.indexOf(s[i]) == -1)
                throw new ParserError("Invalid symbol in argument: " + s.substr(0, i) + "[->" + s[i] + "<-]" + s.substring(i + 1, l)
                    + " at index " + (getIndex(index - 2) + i));
        }
        var result = parseInt(s);
        if (isNaN(result))
            throw new ParserError("Invalid number: [->" + s + "<-] at index " + tokensSize[index - 1]);
        return result;
    }

    function getIndex(i) {
        return tokensSize[i] == undefined ? 0 : tokensSize[i];
    }

}


var parsePrefix = function (expression) {
    return parseExpression(expression, function (bracket) {
        return bracket;
    }, function (tokens) {
        return tokens;
    });
};
var parsePostfix = function (expression) {
    return parseExpression(expression, function (bracket) {
        return bracket == "(" ? ")" : "(";
    }, function (tokens) {
        return tokens.reverse();
    });
};


// print(parsePrefix("(negate x y)"));
// print(parsePostfix("(negate x y)"));