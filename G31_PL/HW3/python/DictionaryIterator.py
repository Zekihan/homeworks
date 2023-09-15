def sort(arr):
    arr1 = arr.copy()
    n = len(arr1)

    for i in range(n):

        for j in range(0, n-i-1):

            if arr1[j] > arr1[j+1] :
                arr1[j], arr1[j+1] = arr1[j+1], arr1[j]
    return arr1

def hasNext(arr):
    if(len(arr)>=1):
        return True
    else:
        return False

def Next(arr):
    sortedArr = sort(arr)
    poppedVal = sortedArr.pop(0)
    arr.remove(poppedVal)
    return poppedVal


inputArr = ["pencil", "brother", "apple", "phone"]
outputArr = []

while(hasNext(inputArr)):
    outputArr.append(Next(inputArr))

print(outputArr)