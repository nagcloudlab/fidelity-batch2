



let numbers = [1, 3, 5, 6, 7, 9, 2, 4, 6, 8, 10];

function compareNumbers(a, b) {
    if (a < b) {
        return -1;
    }
    if (a > b) {
        return 1;
    }
    return 0;
}
numbers.sort(compareNumbers);
console.log(numbers);