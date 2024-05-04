package keyAlgorithm;

public class KeyAlgorithmFunctions {

      // =================================================================

      /**
       * In ra giá trị của từ
       * Lưu ý: 1 từ 32 bit bằng 4 bytes, tương đương 8 kí tự hex
       * 
       * @param w bit của từ để in ra
       */
      public static void ShowWord(int w) {
            for (int i = 1; i <= 8; i++) {
                  int hexa = (w >> 32 - i * 4) & 0xF; // Di chuyển 4 bit và giữ lại 1 kí tự hex
                  System.out.printf("%X", hexa);
            }
            System.out.printf("\n");
      }

      // Hàm hiển thị ma trận
      public static void ShowMatrix(int[] w) {
            // Duyệt tất cả phần tử trong mảng w
            for (int i = 0; i < w.length; i++) {
                  System.out.printf("\n\t");
                  // Gọi hàm hiển thị từ
                  ShowWord(w[i]);
            }
      }

      /**
       * Di chuyển byte trái 1 byte
       * 
       * @param w
       * @return
       */
      public static int RotWord(int w) {
            int byte1 = (w >> 24) & 0xFF; // Di chuyển 24 bit và lấy 1 byte
            int byte234 = w & 0xFFFFFF; // Lấy 3 byte cuối cùng
            int rot = (byte234 << 8) | byte1; // 3 byte cuối được di chuyển 8 bit và nối với byte1

            return rot;
      }

      // Hàm SubWord trả về một khóa mới từ một khóa đã cho (w)
      public static int SubWord(int w) {
            // Mảng S-board
            int S[] = {
                        0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,
                        0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,
                        0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,
                        0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,
                        0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,
                        0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,
                        0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,
                        0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,
                        0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,
                        0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,
                        0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,
                        0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,
                        0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,
                        0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,
                        0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,
                        0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16
            };
            int kq = 0;
            // Biến w có 4 byte
            for (int i = 1; i <= 4; i++) {
                  int bytei = (w >> (32 - i * 8)) & 0xFF; // Lấy byte thứ i mỗi lần
                  int subB = S[bytei]; // Lấy byte con trên bảng S ở chỉ số là byte i
                  kq = (kq << 8) | subB; // Thêm byte con để tạo ra khóa mới
            }

            return kq;
      }

      // Hàm tính toán w với Rcon[j] bằng phép XOR
      public static int XorRcon(int w, int j) {
            // Mảng chứa các giá trị của Rcon[j]
            int Rc[] = {
                        0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
                        0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39
            };
            int byte1 = (w >> 24) & 0xFF; // Lấy byte đầu tiên của w
            int kqXor = (byte1 ^ Rc[j]) & 0xFF; // Thực hiện phép XOR giữa byte1 và Rc[j], lấy 1 byte từ kết quả
            int byte234 = w & 0xFFFFFF; // Lấy 3 byte cuối của w
            int kq = (kqXor << 24) | byte234; // Nối byte234 với byte1

            return kq; // Trả về kết quả
      }

      // Thực hiện trình tự mã hóa
      public static int G(int w, int j) {
            // Xoay W
            int rotW = RotWord(w);
            // Thay thế W
            int subW = SubWord(rotW);
            // Xor Rcon và j
            int kq = XorRcon(subW, j);

            // System.out.printf("\n\tG(%X) = ", w);
            // ShowWord(kq);

            return kq;
      }

      public static int[] KeyExpansion(int[] key) {
            int[] w = new int[44]; // Khởi tạo mảng w có 44 phần tử
            w[0] = key[0];
            w[1] = key[1]; // keyAlgorithm.KeyAlgorithmFunctions.KeyExpansion(KeyAlgorithmFunctions.java:110)
            w[2] = key[2];
            w[3] = key[3];

            for (int i = 4; i < 44; i++) { // Vòng lặp từ 4 đến 43
                  if (i % 4 == 0) { // Nếu chia hết cho 4
                        w[i] = G(w[i - 1], i / 4) ^ w[i - 4]; // Gán giá trị vào w[i]
                  } else { // Ngược lại
                        w[i] = w[i - 1] ^ w[i - 4]; // Gán giá trị vào w[i]
                  }
            }

            return w; // Trả về mảng w
      }

      public static int[] AddRoundKey(int[] state, int[] key) {
            int[] kq = new int[4]; // Khởi tạo mảng kq để lưu kết quả
            kq[0] = state[0] ^ key[0]; // Thực hiện phép toán XOR giữa các vị trí 0 của hai mảng
            kq[1] = state[1] ^ key[1]; // Thực hiện phép toán XOR giữa các vị trí 1 của hai mảng
            kq[2] = state[2] ^ key[2]; // Thực hiện phép toán XOR giữa các vị trí 2 của hai mảng
            kq[3] = state[3] ^ key[3]; // Thực hiện phép toán XOR giữa các vị trí 3 của hai mảng 

            return kq; // Trả về mảng kết quả
      }

      // Hàm này thực hiện phép toán SubBytes trên state được cung cấp
      public static int[] SubBytes(int[] state) {
            int[] kq = new int[4]; // Tạo một mảng để lưu kết quả
            for (int i = 0; i < 4; i++) {
                  kq[i] = SubWord(state[i]); // Thay thế mỗi byte bằng một byte khác từ một bảng tra cứu
            }

            return kq; // Trả về kết quả
      }

      // Phương thức này dùng để dịch chuyển các hàng của mảng 4x4 theo cách sau:
      // Hàng 0 không thay đổi,
      // Hàng 1 dịch byte1 sang trái,
      // Hàng 2 dịch 2 byte sang trái,
      // Hàng 3 dịch 3 byte sang trái.
      public static int[] ShiftRows(int[] state) {
            int[] kq = new int[4];

            // Duyệt từng hàng trong mảng
            for (int i = 0; i < 4; i++) {
                  // Lấy byte1 từ hàng hiện tại
                  int byte1 = state[i] & 0xFF000000;
                  // Lấy byte2 từ hàng kế tiếp
                  int byte2 = state[(i + 1) % 4] & 0xFF0000;
                  // Lấy byte3 từ hàng tiếp theo nữa
                  int byte3 = state[(i + 2) % 4] & 0xFF00;
                  // Lấy byte4 từ hàng tiếp theo nữa nữa
                  int byte4 = state[(i + 3) % 4] & 0xFF;

                  // Kết hợp 4 byte thành 1 từ
                  kq[i] = byte1 | byte2 | byte3 | byte4;
            }

            return kq;
      }

      public static int MultiplyTwo(int w) { // Hàm nhân hai với số được truyền vào
            int kq = w << 1; // Xác định tích của hai số
            if (kq > 256) { // Nếu tích lớn hơn 256
                  kq = kq ^ 0x11b; // Thực hiện phép biên dịch XOR
            }
            kq = kq & 0xFF; // Dịch bit mặt dịch 8
            return kq; // Trả về kết quả
      }

      // Hàm này nhân 3 với một số
      public static int MultiplyThree(int w) {
            // Tính kết quả bằng phép lũy thừa ^ và gán vào biến kq
            int kq = w ^ MultiplyTwo(w);
            // Chuyển giá trị của kq về dạng 8 bit
            kq = kq & 0xFF;

            return kq;
      }

      // Hàm nhân ma trận 4 byte với các hệ số cho trước
      public static int MultiplyColumn(int w) {
            int kq;
            int byte1 = (w >> 24) & 0xFF; // Lấy 8 bit cuối của byte 1
            int byte2 = (w >> 16) & 0xFF; // Lấy 8 bit cuối của byte 2
            int byte3 = (w >> 8) & 0xFF; // Lấy 8 bit cuối của byte 3
            int byte4 = w & 0xFF; // Lấy 8 bit cuối của byte 4

            int kq1 = MultiplyTwo(byte1) ^ MultiplyThree(byte2) ^ byte3 ^ byte4; // Tính giá trị của byte 1 sau khi nhân
            int kq2 = byte1 ^ MultiplyTwo(byte2) ^ MultiplyThree(byte3) ^ byte4; // Tính giá trị của byte 2 sau khi nhân
            int kq3 = byte1 ^ byte2 ^ MultiplyTwo(byte3) ^ MultiplyThree(byte4); // Tính giá trị của byte 3 sau khi nhân
            int kq4 = MultiplyThree(byte1) ^ byte2 ^ byte3 ^ MultiplyTwo(byte4); // Tính giá trị của byte 4 sau khi nhân

            kq = (kq1 << 24) | (kq2 << 16) | (kq3 << 8) | kq4; // Trả về toàn bộ giá trị đã nhân của 4 byte

            return kq;
      }

      // Hàm biến đổi các cột của ma trận state
      public static int[] MixColumns(int[] state) {
            int[] kq = new int[4]; // Khởi tạo mảng kết quả 4 phần tử
            // System.out.printf("\nMixColumns\n");
            for (int i = 0; i < 4; i++) { // Lặp vòng lặp 4 lần
                  kq[i] = MultiplyColumn(state[i]); // Mỗi phép nhân lần lượt 4 cột của ma trận state
            }

            return kq; // Trả về mảng kết quả
      }

      // Inverse Algorithm
      // =================================================================

      // Hàm InvShiftRows dùng để thực hiện việc lấy mỗi byte của state array rồi trộn
      // lại thành 1 word và lưu kết quả vào kq
      public static int[] InvShiftRows(int[] state) {
            int[] kq = new int[4];

            // Duyệt qua từng byte của state array
            for (int i = 0; i < 4; i++) {
                  // Tách từng byte của state array
                  int byte1 = state[i] & 0xFF000000;
                  int byte2 = state[(i + 3) % 4] & 0xFF0000;
                  int byte3 = state[(i + 2) % 4] & 0xFF00;
                  int byte4 = state[(i + 1) % 4] & 0xFF;

                  // Kết hợp các bytes thành 1 word và lưu kết quả vào mảng kq
                  kq[i] = byte1 | byte2 | byte3 | byte4;
            }

            return kq;
      }

      // Hàm dùng để thực hiện việc ánh xạ các byte của số được truyền vào với bảng
      // S-box inverse
      public static int InvSubWords(int w) {
            int InvS[] = { // Bảng inverse S-box
                        0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,
                        0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,
                        0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,
                        0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,
                        0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,
                        0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,
                        0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,
                        0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,
                        0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,
                        0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,
                        0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,
                        0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,
                        0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,
                        0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,
                        0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,
                        0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
            };

            int kq = 0; // Khởi tạo biến lưu giá trị trả về
            for (int i = 1; i <= 4; i++) { // Duyệt 4 byte của số được chuyền vào
                  int bytei = (w >> (32 - i * 8)) & 0xFF; // Lấy từng byte từ hàng động của số đã truyền vào
                  int subB = InvS[bytei]; // Ánh xạ thông qua bảng inverse S-box
                  kq = (kq << 8) | subB; // Gán kết quả ánh xạ vào giá trị trả về
            }

            return kq; // Trả về kết quả
      }

      // Hàm thực hiện đảo ngược các subwords trong mảng state
      public static int[] InvSubBytes(int[] state) {
            int[] kq = new int[4]; // Mảng lưu kết quả

            for (int i = 0; i < 4; i++) { // Vòng lặp duyệt các giá trị trong mảng state
                  kq[i] = InvSubWords(state[i]); // Đảo ngược mỗi subword và lưu vào mảng kq
            }
            return kq; // Trả về kết quả
      }

      public static int MultiplyNine(int w) {
            int kq = (w << 3) ^ w;
            // Nếu kq lớn hơn 256 bits << 2
            if (kq > (256 << 2))
                  kq = kq ^ (0x11b << 2);
            // Nếu kq lớn hơn 256 bits << 1
            if (kq > (256 << 1))
                  kq = kq ^ (0x11b << 1);
            // Nếu kq lớn hơn 256 bits
            if (kq > 256)
                  kq = kq ^ 0x11b;

            // Lấy 8 bytes cuối cùng của kq
            kq = kq & 0xFF;

            return kq;
      }

      public static int MultiplyB(int w) {
            int kq = (w << 3) ^ (w << 1) ^ w;
            // Nếu kq lớn hơn 256 bits << 2
            if (kq > (256 << 2))
                  kq = kq ^ (0x11b << 2);
            // Nếu kq lớn hơn 256 bits << 1
            if (kq > (256 << 1))
                  kq = kq ^ (0x11b << 1);
            // Nếu kq lớn hơn 256 bits
            if (kq > 256)
                  kq = kq ^ 0x11b;

            // Lấy 8 bytes cuối cùng của kq
            kq = kq & 0xFF;

            return kq;
      }

      public static int MultiplyD(int w) {
            int kq = (w << 3) ^ (w << 2) ^ w;
            // Nếu kq lớn hơn 256 bits << 2
            if (kq > (256 << 2))
                  kq = kq ^ (0x11b << 2);
            // Nếu kq lớn hơn 256 bits << 1
            if (kq > (256 << 1))
                  kq = kq ^ (0x11b << 1);
            // Nếu kq lớn hơn 256 bits
            if (kq > 256)
                  kq = kq ^ 0x11b;

            // Lấy 8 bytes cuối cùng của kq
            kq = kq & 0xFF;

            return kq;
      }

      public static int MultiplyE(int w) {
            int kq = (w << 3) ^ (w << 2) ^ (w << 1);
            // Nếu kq lớn hơn 256 bits << 2
            if (kq > (256 << 2))
                  kq = kq ^ (0x11b << 2);
            // Nếu kq lớn hơn 256 bits << 1
            if (kq > (256 << 1))
                  kq = kq ^ (0x11b << 1);
            // Nếu kq lớn hơn 256 bits
            if (kq > 256)
                  kq = kq ^ 0x11b;

            // Lấy 8 bytes cuối cùng của kq
            kq = kq & 0xFF;

            return kq;
      }

      // Hàm tìm số nghịch đảo của hàng của ma trận (w)
      public static int InvMultiplyColumns(int w) {
            int kq; // Khởi tạo biến kq lưu kết quả cuối cùng
            int byte1 = (w >> 24) & 0xFF; // Lấy 8 bit cao nhất của số w và chuyển thành dạng tự nhiên
            int byte2 = (w >> 16) & 0xFF; // Lấy 8 bit thứ 2 của số w và chuyển thành dạng tự nhiên
            int byte3 = (w >> 8) & 0xFF; // Lấy 8 bit thứ 3 của số w và chuyển thành dạng tự nhiên
            int byte4 = w & 0xFF; // Lấy 8 bit thứ 4 của số w và chuyển thành dạng tự nhiên

            int kq1 = MultiplyE(byte1) ^ MultiplyB(byte2) ^ MultiplyD(byte3) ^ MultiplyNine(byte4); // Tích chập ma trận
                                                                                                    // (E, B, D, 9) với
                                                                                                    // 4 bytes của số w.
            int kq2 = MultiplyNine(byte1) ^ MultiplyE(byte2) ^ MultiplyB(byte3) ^ MultiplyD(byte4); // Tích chập ma trận
                                                                                                    // (9, E, B, D) với
                                                                                                    // 4 bytes của số w.
            int kq3 = MultiplyD(byte1) ^ MultiplyNine(byte2) ^ MultiplyE(byte3) ^ MultiplyB(byte4); // Tích chập ma trận
                                                                                                    // (D, 9, E, B) với
                                                                                                    // 4 bytes của số w.
            int kq4 = MultiplyB(byte1) ^ MultiplyD(byte2) ^ MultiplyNine(byte3) ^ MultiplyE(byte4); // Tích chập ma trận
                                                                                                    // (B, D, 9, E) với
                                                                                                    // 4 bytes của số w.

            kq = (kq1 << 24) | (kq2 << 16) | (kq3 << 8) | kq4; // Ghép 4 kết quả của bước trên vào biến kq.
            return kq; // Trả về kết quả tìm được
      }

      public static int[] InvMixColumns(int[] state) {
            int[] kq = new int[4];

            // Dùng vòng lặp for để xét tất cả 4 phần tử trong mảng state
            for (int i = 0; i < 4; i++) {
                  // Tính toán nghịch đảo cột của phần tử thứ i trong mảng state
                  kq[i] = InvMultiplyColumns(state[i]);
            }
            // Trả về kết quả
            return kq;
      }
}
