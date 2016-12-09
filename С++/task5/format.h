//
// Created by Blackbird on 18.05.2016.
//

#ifndef TASK4_FORMAT_H
#define TASK4_FORMAT_H

#include <string>
#include <stdexcept>
#include <iostream>
#include <type_traits>
#include <typeinfo>

template<typename ...Args>
std::string format(const std::string &formatString, const Args &... args);

enum Format {
    def = '\0',

    d = 'd',
    i = 'i',
    u = 'u',
    o = 'o',
    x = 'x',
    X = 'X',
    f = 'f',
    F = 'F',
    e = 'e',
    E = 'E',
    g = 'g',
    G = 'G',
    a = 'a',
    A = 'A',
    c = 'c',
    s = 's',
    p = 'p',
    n = 'n',
    automatc = '@'
};


std::string read_format(const std::string &fmt, size_t &index, bool &width_arg, bool &precision_arg,
                        Format &f);

// Replace '*' in format on the the corresponding value of width or precision
std::string replace_format(const std::string &fmt, int &wp);

// Check string type
template<typename T>
bool check_type(Format format, T variable,
                typename std::enable_if<(std::is_convertible<T, std::string>::value), std::string>::type * = 0) {
    if (format == p || format == s) {
        return true;
    } else {
        throw std::invalid_argument("Invalid argument: string expected");
    }
}

//Check type pointers type
template<typename T>
bool check_type(Format format, T variable,
                typename std::enable_if<(!(std::is_convertible<T, std::string>::value) &&
                                         (std::is_pointer<T>::value)), std::string>::type * = 0) {
    return format == p;
}

//Check numbers or char type
template<typename T>
bool check_type(Format format, T variable, typename std::enable_if<
        !(std::is_convertible<T, std::string>::value) && !(std::is_pointer<T>::value), std::string>::type * = 0) {
    if (!std::is_convertible<T, int>::value) {
        throw std::invalid_argument("Invalid argument");
    }
    if (format == d || format == i || format == u || format == o || format == x || format == X || format == f ||
        format == F || format == e || format == E || format == g || format == G || format == a || format == A ||
        format == c) {
        return true;
    }
    throw std::invalid_argument("Invalid argument");
}

// Returns formatted argument For std::string arg need conversion to char * type
template<typename T>
std::string get_format_string(T &arg, Format &format, std::string &format_str,
                              typename std::enable_if<std::is_same<T, std::string>::value ||
                                                      std::is_same<T, const std::string>::value>::type * = 0) {
    char *arg_c = const_cast<char *>(arg.c_str());
    if (!check_type(format, arg_c)) {
        throw std::invalid_argument("Format : invalid type of  argument");
    }
    std::string result((size_t) snprintf(NULL, 0, const_cast<char *>(format_str.c_str()), arg_c), '\0');
    snprintf(const_cast<char *>(result.c_str()), result.length() + 1, const_cast<char *>(format_str.c_str()),
             arg_c);
    return result;
}

// Returns formatted arguments for other types
template<typename T>
std::string get_format_string(T &arg, Format &format, std::string &format_str, typename std::enable_if<
        !std::is_same<T, std::string>::value && !std::is_same<T, const std::string>::value>::type * = 0) {
    // This check doesn't work
    if (!check_type(format, arg)) {
        throw std::invalid_argument("Format : invalid type of  argument");
    }
    std::string result((size_t) snprintf(NULL, 0, const_cast<char *>(format_str.c_str()), arg), '\0');
    snprintf(const_cast<char *>(result.c_str()), result.length() + 1, const_cast<char *>(format_str.c_str()), arg);
    return result;
}


// Convert arg to int. Need for read width and precision
template<typename T>
int read_int(T &arg, typename std::enable_if<std::is_integral<T>::value>::type * = 0) {
    return (int) arg;
}

template<typename T>
int read_int(T &arg, typename std::enable_if<!std::is_integral<T>::value>::type * = 0) {
    throw std::invalid_argument(std::string("Invalid argument, expected integral type"));
}

// Read width, precision and argument and returns formatted argument
template<typename T1, typename T2, typename T3, typename ...Args>
std::string format_3_args(std::string &formatSpec, Format &format, T1 &width, T2 &precision, T3 &arg,
                          Args &... rest) {
    int width_val = read_int(width);
    formatSpec = replace_format(formatSpec, width_val);
    int precision_val = read_int(precision);
    formatSpec = replace_format(formatSpec, precision_val);
    return get_format_string(arg, format, formatSpec);
};

template<typename ...Args>
std::string format_3_args(std::string &formatSpec, Format &f, Args &... rest) {
    throw std::out_of_range("Not enough arguments");
}

// Read width or precision and returns formatted argument
template<typename T1, typename T2, typename ...Args>
std::string format_2_args(std::string &formatSpec, Format &format, T1 &ws, T2 &arg, Args &... rest) {
    int val = read_int(ws);
    formatSpec = replace_format(formatSpec, val);
    return get_format_string(arg, format, formatSpec);
};

template<typename ...Args>
std::string format_2_args(std::string &formatSpec, Format &f, Args &... rest) {
    throw std::out_of_range("Not enough arguments");
}

// Read argument and returns formatted argument
template<typename T, typename ...Args>
std::string format_arg(std::string &formatSpec, Format &format, T &arg, Args &... rest) {
    return get_format_string(arg, format, formatSpec);
};

template<typename ...Args>
std::string format_arg(std::string &formatSpec, Format &f, Args &... rest) {
    throw std::out_of_range("Not enough arguments");
}


template<typename T>
std::string get_name(T &arg) {
    std::string type = typeid(*arg).name();
    return type == "i" ? "int" : (type == "Ss" ? "std::string" : (type == "d" ? "double" : (type == "f" ? "float"
                                                                                                        : type)));
}

template<typename T>
typename std::enable_if<std::is_convertible<T, std::string>::value, std::string>::type
print_auto(const T &arg) {
    return arg;
}

template <typename T>
typename std::enable_if<!std::is_convertible<T, std::string>::value && std::is_same<T, void * const>::value, std::string>::type print_auto(T &arg){
    return "nullptr<void>";
};

template<typename T>
typename std::enable_if<!std::is_convertible<T, std::string>::value && std::is_pointer<T>::value && !std::is_same<T, void * const>::value, std::string>::type print_auto(T &arg) {
    std::string type = get_name(arg), result;
    if(arg == 0){
        result.append("nullptr<").append(type).append(">");
    } else{
        result.append("ptr<").append(type).append(">(").append(format("%@", *arg)).append(")");
    }
    return result;
};



template<typename T, size_t size> typename std::enable_if<!std::is_convertible<T*, std::string>::value, std::string>::type
print_auto(const T(&arg) [size]){
    std::string result = "[";
    for(int i =0; i<size - 1; i++){
        result += std::to_string(arg[i]) + ",";
    }
    result += std::to_string(arg[size - 1]) + "]";
    return result;
};


template<typename T>
typename std::enable_if<std::is_integral<T>::value, std::string>::type print_auto(T arg){
    return std::to_string(arg);
};


template<typename T>
typename std::enable_if<!std::is_pointer<T>::value && !std::is_integral<T>::value && !std::is_convertible<T, std::string>::value, std::string>::type
print_auto(const T &arg) {
    throw std::invalid_argument("%@");
}

// Processes rest of format (without arguments) and throws out_of_range if format declared some
template<typename ...Args>
std::string sprint(const std::string &fmt, int &count_of_args, size_t &index, Args &... rest) {
    if (index < fmt.length()) {
        std::string formatSpec, result = "";
        bool width_arg = false, precision_arg = false;
        Format format = def;
        do {
            formatSpec = read_format(fmt, index, width_arg, precision_arg, format);
            if (formatSpec[0] != '%') {
                result += formatSpec;
            } else if (formatSpec == "%") {
                result += formatSpec;
            } else {
                throw std::out_of_range("Format : not enough arguments");
            }
        } while (index < fmt.length());
        return result;
    }
    return "";
}

// Read format, read the required number of arguments and formatted it
template<typename T, typename ...Args>
std::string sprint(const std::string &fmt, int &count_of_extraArgs, size_t &index, T &head, Args &... rest) {
    // If we use some args (for precision, width) we need skip it
    if (count_of_extraArgs > 0) {
        return sprint(fmt, --count_of_extraArgs, index, rest...);
    }
    size_t len = fmt.length();
    std::string formatSpec;
    while (index < len) {
        bool width_arg = false, precision_arg = false;
        Format format = def;
        std::string temp;
        do {
            formatSpec = read_format(fmt, index, width_arg, precision_arg, format);
            temp += formatSpec;
        } while (formatSpec == "%" || formatSpec[0] != '%');
        if (format == automatc) {
            if (typeid(head).name() == typeid(std::nullptr_t).name()) {
                return "nullptr" + sprint(fmt, count_of_extraArgs, index, rest...);
            }
            return print_auto(head) + sprint(fmt, count_of_extraArgs, index, rest...);
        }

        formatSpec = temp;
        count_of_extraArgs = width_arg + precision_arg;
        std::string result;
        result = count_of_extraArgs == 0 ? format_arg(formatSpec, format, head, rest...) :
                 (count_of_extraArgs == 1 ? format_2_args(formatSpec, format, head, rest...) :
                  format_3_args(formatSpec, format, head, rest...));
        return result + sprint(fmt, count_of_extraArgs, index, rest...);
    }
    throw std::invalid_argument("Empty format and extra args");
}

/**
 * Returns a formatted string using the specified format string and
 * arguments.
 *
 * @param  format
 * Format is the same as in printf()
 *
 *    Additional format specifier %@:
 *
 *    1) if type of argument is nullptr_t returns "nullptr_t"
 *    2) if argument is pointer:
 *    a) if pointer is nullptr returns "nullptr<type name>"
 *    b) otherwise returns "ptr<typename>(the output values)"
 *    3) if argument is an array returns the string that contains the elements of an array inf fromat [a1,a2,...]
 *    4) if argument can be casted to std:string returns result of the cast
 *    5) otherwise throws std::invalid_argument
 *
 * @param  args
 *         Arguments referenced by the format specifiers in the format
 *         string.  If there are more or less arguments than format specifiers,
 *         throws std::out_of_range. The number of arguments is
 *         variable and may be zero.
 *
 * @throws  std::invalid_argument
 *          If a format string contains an illegal syntax,
 *          insufficient arguments given the format string, or other
 *          illegal conditions.
 *
 * @throws  std::out_of_range
 *          If the arguments' list contains too few or too many arguments
 *
 * @return  A formatted string
 */
template<typename ...Args>
std::string format(const std::string &formatString, const Args &... args) {
    int count_of_args = 0;
    size_t index = 0;
    return sprint(formatString, count_of_args, index, args...);
}


#endif //TASK4_FORMAT_H