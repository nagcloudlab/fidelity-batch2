


numbers=[1, 2, 4, 5, 3]
# sort the list in ascending order
numbers.sort()
print("Sorted list in ascending order:", numbers)
# sort the list in descending order by lambda function
numbers.sort(key=lambda x: x, reverse=True)
print("Sorted list in descending order:", numbers)  