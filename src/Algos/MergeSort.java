package Algos;

class MergeSort {
    // Driver code
    public static void main(String args[]) {
        int arr[] = {12, 11, 13, 5, 6, 7};
        printArray(arr);
        MergeSort ob = new MergeSort();
        ob.sort(arr, 0, arr.length - 1);
        printArray(arr);
    }

    void sort(int arr[], int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        int[] leftArray = new int[n1], rightArray = new int[n2];

        for (int i = 0; i < n1; ++i) leftArray[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) rightArray[j] = arr[m + 1 + j];

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k++] = leftArray[i];
                i++;
            } else {
                arr[k++] = rightArray[j];
                j++;
            }
        }

        while (i < n1) arr[k++] = leftArray[i++];
        while (j < n2) arr[k++] = rightArray[j++];
    }

    static void printArray(int arr[]) {
        for (int j : arr) System.out.print(j + " ");
        System.out.println();
    }
}