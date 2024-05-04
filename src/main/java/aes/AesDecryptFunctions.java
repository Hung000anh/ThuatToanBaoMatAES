package aes;

import java.util.Arrays;

import keyAlgorithm.KeyAlgorithmFunctions;

public class AesDecryptFunctions {
      // Hàm này áp dụng giải thuật AES bằng cách sử dụng văn bản đã cho và khóa.
      public static int[] AesDecrypt(int[] C, int[] key) {
            int[] w = KeyAlgorithmFunctions.KeyExpansion(key); // Tạo ra khóa mở rộng
            int[] state = KeyAlgorithmFunctions.AddRoundKey(C, Arrays.copyOfRange(w, 40, 44));

            // Khởi tạo một vòng lặp để duyệt từ vòng 1 đến 9
            for (int i = 1; i <= 9; i++) {
                  state = KeyAlgorithmFunctions.InvShiftRows(state); // Áp dụng hàm shift rows trên trạng thái hiện tại

                  state = KeyAlgorithmFunctions.InvSubBytes(state); // Áp dụng hàm SubBytes trên trạng thái hiện tại

                  // Thêm vòng khóa cho mỗi lần lặp
                  state = KeyAlgorithmFunctions.AddRoundKey(state, Arrays.copyOfRange(w, 40 - 4 * i, 40 - 4 * (i - 1)));

                  state = KeyAlgorithmFunctions.InvMixColumns(state); // Áp dụng hàm MixColumns trên trạng thái hiện tại
            }

            // Vòng 10: Áp dụng hàm Inverse Shift Rows, InvSubBytes và Add Round Key
            state = KeyAlgorithmFunctions.InvShiftRows(state);
            state = KeyAlgorithmFunctions.InvSubBytes(state);
            state = KeyAlgorithmFunctions.AddRoundKey(state, Arrays.copyOfRange(w, 0, 4));
            
            return state;
      }

      public static void main(String[] args) {
            int w0 = 0x2b7e1516, w1 = 0x28aed2a6, w2 = 0xabf71588, w3 = 0x09cf4f3c;
            int key[] = { w0, w1, w2, w3 };
            int state[] = new int[4];
            state[0] = 0x3243f6a8;
            state[1] = 0x885a308d;
            state[2] = 0x313198a2;
            state[3] = 0xe0370734;

            int[] C = AesEncryptFunctions.AesEncrypt(state, key);

            System.out.printf("\nBan ma: \n");
            KeyAlgorithmFunctions.ShowMatrix(C);

            int[] M = AesDecrypt(C, key);

            System.out.printf("\nBan ro: \n");
            KeyAlgorithmFunctions.ShowMatrix(M);
      }
}
