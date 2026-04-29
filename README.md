# README.md

# Flowgame Mèo Sinh Tồn

## Tổng quan dự án

Flowgame Mèo Sinh Tồn là game 2D kể chuyện tương tác (Interactive Narrative Game) được phát triển bằng **Java** sử dụng framework **LibGDX**.

Người chơi vào vai một chú mèo đứng trước lựa chọn quan trọng:

* Đi theo chủ để sống an toàn trong nhà
* Trốn ra ngoài để sống tự do nơi đường phố

Từ quyết định đầu tiên, game sẽ mở ra nhiều tuyến truyện khác nhau, các minigame, hệ thống chỉ số và nhiều ending.

---

# Vì sao chọn LibGDX

LibGDX là framework phát triển game 2D đa nền tảng bằng Java.

Dự án chọn LibGDX vì:

* Viết bằng Java, dễ quản lý code OOP
* Hỗ trợ game 2D mạnh
* Có Scene management / Screen system
* Quản lý input, âm thanh, animation
* Có thể build cho Windows, Android, Desktop
* Phù hợp nhóm sinh viên / indie team

---

# Loại game

* 2D Story Game
* Branching Narrative
* Choice-based Adventure
* Mini-game Integrated Game
* Multiple Ending Game

---

# Góc nhìn game

Game là **2D UI-based Adventure**.

Người chơi chủ yếu tương tác qua:

* nền 2D (background)
* nhân vật mèo 2D
* textbox hội thoại
* nút lựa chọn
* minigame đơn giản

Không phải game 3D thế giới mở.

---

# Gameplay chính

Game hoạt động theo chu kỳ:

```text
Hiển thị Scene
→ Người chơi đọc nội dung
→ Chọn quyết định
→ Cập nhật chỉ số
→ Nếu có thì mở Minigame
→ Chuyển Scene tiếp theo
→ Đi đến Ending
```

---

# Hệ thống tuyến truyện

## 1. Home Route

Người chơi đi theo chủ.

### Đặc điểm:

* an toàn
* được cho ăn
* ít nguy hiểm
* bị kiểm soát
* xung đột nội tâm

### Ví dụ sự kiện:

* khám phá nhà
* cỏ mèo
* bị tắm
* phòng khám
* sống trong lồng vàng

---

## 2. Street Route

Người chơi bỏ đi ra đường.

### Đặc điểm:

* tự do
* nguy hiểm
* săn mồi
* băng mèo
* bị bắt trộm

### Ví dụ sự kiện:

* tranh lãnh thổ
* đánh nhau
* cứu mèo khác
* trở thành đại ca

---

## 3. Drama Route (phụ)

Kích hoạt khi mèo có bạn đời.

### Ví dụ:

* ngoại tình
* theo dõi vợ
* đánh ghen
* tranh giành tình cảm

---

# Hệ thống chỉ số

Game có hệ state để ảnh hưởng ending.

## Các chỉ số:

* HP: sức khỏe
* Karma: hướng thiện / phản kháng
* Attack: sức mạnh
* Energy: năng lượng

## Cờ trạng thái:

* has_wife
* saved_cats
* joined_gang
* escaped_home

---

# Minigame trong game

Một số scene sẽ chuyển sang gameplay ngắn.

## Ví dụ minigame:

* Mê cung cống ngầm
* Trốn bắt mèo
* Battle xà phòng
* Săn chuột
* Reaction game

## Vai trò:

* tăng tương tác
* tránh chỉ đọc text
* tạo thắng / thua
* ảnh hưởng route

---

# Cách game được xây bằng LibGDX

# Kiến trúc tổng thể

Game chia thành nhiều Screen.

## Các Screen chính:

### StoryScreen

Hiển thị:

* background
* mèo
* textbox
* choice button

### MinigameScreen

Chạy minigame riêng.

### EndingScreen

Hiển thị ending cuối game.

---

# Luồng hoạt động trong LibGDX

```text
MainGame.java khởi động
→ StoryScreen
→ người chơi chọn
→ nếu minigame:
   chuyển MinigameScreen
→ quay lại StoryScreen
→ EndingScreen
```

---

# Hệ thống dữ liệu Scene

Game dùng cấu trúc Scene.

Ví dụ:

```text
Scene ID: H2

Text:
Bạn khám phá căn nhà mới.

Choices:
1. Thử cỏ mèo → H2m
2. Nghỉ ngơi → H3
```

---

# Trong code Java:

```java
class Scene {
   String id;
   String text;
   Array<Choice> choices;
   SceneType type;
}
```

---

# Rendering 2D trong LibGDX

LibGDX dùng:

* SpriteBatch để vẽ ảnh 2D
* Texture để load asset
* BitmapFont để hiển thị text
* Stage/UI để tạo button

---

# Ví dụ hiển thị scene:

```text
[Background House]

Con mèo nhìn quanh căn phòng mới...

[1] Khám phá
[2] Ngủ
```

---

# Save / Load

Game có thể lưu:

* scene hiện tại
* HP
* Karma
* route đang đi
* flags

Dùng JSON hoặc Preferences của LibGDX.

---

# MVP Version đầu tiên

Phiên bản đầu:

* Opening
* Home Route hoàn chỉnh
* 1 minigame
* 2 ending
* save/load
* UI cơ bản

---

# Hướng mở rộng

* Pixel art animation
* Sound effect
* Nhiều route hơn
* Achievement
* Gallery ending
* Mobile version

---

# Điểm mạnh dự án

* Cốt truyện khác lạ
* Có replay value
* Nhiều ending
* Dễ mở rộng thêm content
* Phù hợp team Java dùng LibGDX

---

# Thông điệp game

> Tự do và an toàn hiếm khi cùng tồn tại.
> Người chơi phải tự quyết định cái giá mình chấp nhận.

---
