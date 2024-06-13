def shuffle(arr):
    if len(arr) == 1:
        return arr
    third_len = len(arr) // 3
    first = arr[:third_len]
    second = arr[third_len:2*third_len]
    third = arr[2*third_len:]
    first = shuffle(first)
    second = shuffle(second)
    third = shuffle(third)
    return third + first + second

def main():
    N = int(input().strip())
    array_elements = list(map(int, input().strip().split()))
    if len(array_elements) != N:
        raise ValueError("")
    
    result = shuffle(array_elements)
    print(' '.join(map(str, result)))

if __name__ == '__main__':
    main()