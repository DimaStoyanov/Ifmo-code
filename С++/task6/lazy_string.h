#ifndef LAZY_STRING_H
#define LAZY_STRING_H

#include <string>
#include <memory>
#include <iostream>



// Class lazy string for string implements copy-on-write

class lazy_string {
    struct char_reference {
    public:
        // Implements write on lazy_string
        char_reference &operator=(char);

        friend class lazy_string;

    private:
        char_reference(lazy_string *, size_t);

        const size_t index;
        lazy_string *const lazy_string_ptr;
    };

public:

    /*
     * Constructs new lazy_string from received std::string
     * @param First argument is std::string
     */
    lazy_string(const std::string &);

    /*
     * Constructs empty lazy_string
     */
    lazy_string();

    /*
     * Cast to std::string with copy of data from current lazy_string
     *
     * @return std::string with copy of data from current lazy_string
     */
    operator std::string();

    /*
     * Returns count of characters in current lazy_string
     *
     * @return Count of characters in current lazy_string
     */
    size_t size() const;

    /*
     * Returns count of characters in current lazy_string
     *
     * @return Count of characters in current lazy_string
     */
    size_t length() const;

    /*
     * Returns a character at the specified index
     * in case of invalid index throws std::out_of_range
     *
     * @param First arguments is index of the character to return
     *
     * @return a character at the specified index
     *
     * @throws std::out_of_range
     */
    char at(size_t) const;

    char_reference at(size_t);

    /*
     * Returns a character at the specified index
     * does not check correctness of index
     * @param First arguments is index of the character to return
     *
     * @return a character at the specified index
     */

    char operator[](size_t) const;

    char_reference operator[](size_t);


    /*
     * Reads a data from the stream and stores it in a lazy_string(overwritting)
     *
     * @param First argument std::istream object, where data is read
     * @param Second argument lasy_string object, where the read data is stored
     *
     * @return First argument
     */
    friend std::istream &operator>>(std::istream &, lazy_string &);

    /*
     * Inserts data from lazy_string into std::ostream
     *
     * @param First argument std::ostream object where data are inserted
     * @param Second argument lazy_string object with data to insert
     *
     * @return First argument
     */
    friend std::ostream &operator<<(std::ostream &, lazy_string &);

    /*
     * Returns the substring including a left border, and not including the right
     * If first argument is incorrect throw std::out_of_range
     * If second argument is incorrect, returns substring from first argument to the end of line
     *
     * @param First argument is index of first character of substring
     * @param Second argument is length of required substring
     *
     * @return lazy_string containing characters  [start, start + lenOfSubstring]
     * @return substring containing characters [start, str.len()] if start + lenOfSubstring > str.len()
     * @return empty lazy string if start == length of string
     *
     * @throws std::out_of_range
     */
    lazy_string substr(size_t, size_t);


private:


    std::shared_ptr<std::string> string_ptr;
    size_t start_index, string_length;

};

#endif //LAZY_STRING_H