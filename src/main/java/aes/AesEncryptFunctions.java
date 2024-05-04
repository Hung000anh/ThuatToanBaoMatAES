package aes;

import java.util.Arrays;

import keyAlgorithm.KeyAlgorithmFunctions;

public class AesEncryptFunctions {

      // Hàm mã hóa bằng giải thuật AES
      public static int[] AesEncrypt(int[] state, int[] key) {
    	      	  
            // Thực hiện Mở Rộng Khóa trên khóa đã cho
            int[] w = KeyAlgorithmFunctions.KeyExpansion(key);
            // Lấy 4 chỉ số đầu tiên từ mảng mở rộng và thực hiện AddRoundKey
            state = KeyAlgorithmFunctions.AddRoundKey(state, Arrays.copyOfRange(w, 0, 4)); 

            // Lặp qua 9 vòng mã hóa
            for (int i = 1; i <= 9; i++) {
                  // Thực hiện SubBytes trong mỗi vòng
                  state = KeyAlgorithmFunctions.SubBytes(state);
                  // Thực hiện ShiftRows trong mỗi vòng
                  state = KeyAlgorithmFunctions.ShiftRows(state);
                  // Thực hiện MixColumns trong mỗi vòng
                  state = KeyAlgorithmFunctions.MixColumns(state);
                  // Thực hiện AddRoundKey trong mỗi vòng dùng các chỉ số tương ứng trong mảng mở
                  // rộng
                  state = KeyAlgorithmFunctions.AddRoundKey(state, Arrays.copyOfRange(w, i * 4, i * 4 + 4));
            }

            // Vòng 10 - chỉ thực hiện các hoạt động SubBytes, ShiftRows và AddRoundKey
            state = KeyAlgorithmFunctions.SubBytes(state);
            state = KeyAlgorithmFunctions.ShiftRows(state);
            state = KeyAlgorithmFunctions.AddRoundKey(state, Arrays.copyOfRange(w, 10 * 4, 10 * 4 + 4));

            // System.out.printf("\nRound 10: \n");
            // ShowMatrix(state);

            // Trả về kết quả
            int[] kq = new int[4];
            kq = state;
            return kq;
      }

      public static void main(String[] args) {
            int w0 = 0x2b7e1516, w1 = 0x28aed2a6, w2 = 0xabf71588, w3 = 0x09cf4f3c;
            int key[] = { w0, w1, w2, w3 };
            int state[] = new int[4];
            state[0] = 0x3243f6a8;
            state[1] = 0x885a308d;
            state[2] = 0x313198a2;
            state[3] = 0xe0370734;

            int[] C = AesEncrypt(state, key);

            System.out.printf("\nBan ma: \n");
            KeyAlgorithmFunctions.ShowMatrix(C);
      }

}
