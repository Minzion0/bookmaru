# 📕 도서 대출 관리 시스템 : BookMaru

도서 대출 관리 시스템은 도서관에서 도서의 대출 및 관리를 위한 시스템입니다.

## 프로젝트 기능 및 설계

### 💁🏻‍♂️ 회원가입 및 로그인 기능

사용자는 회원가입을 통해 계정을 생성하고 로그인할 수 있습니다.

- 🆔 사용자는 `name`, `password`, `birthdate`, `email`을 입력하여 회원가입을 할 수 있습니다
- 👥 회원은 관리자(admin)와 일반 사용자(user) 역할을 가지고 있습니다.

### 📚 도서 관리 기능

관리자는 도서를 추가하고 삭제할 수 있습니다.

- 📖 도서는 제목, 저자, 출판사, ISBN 등의 정보를 포함합니다.
- 📦 재고 수량이 관리됩니다.

### 📚 대출 기능

사용자는 로그인 후 도서를 대출할 수 있습니다.

- 📚 한 번에 최대 **5권**의 도서를 대출할 수 있습니다.
- ⏳ 대출 기한은 대출일로부터 2주이며, 연장은 불가능합니다.

### 🔄 반납 기능

사용자는 대출한 도서를 반납할 수 있습니다.

- 📚 도서 반납 후 재고 수량이 업데이트됩니다.

### ✍️ 후기 및 댓글 기능

사용자는 대출한 도서에 후기를 작성하고 댓글을 달 수 있습니다.

- ✍️ 후기는 CRUD(Create, Read, Update, Delete) 기능을 지원합니다.
- 💬 댓글은 해당 후기에 대해 작성됩니다.
- ✍️ 관리자는 후기 및 댓글 삭제 처리 할수있습니다.
### 🌟 도서 추천 기능

도서 추천은 사용자에게 맞춤형 정보를 제공합니다.

- 🏆 메인 페이지에서는 **월간 도서 Top 5**와 **장르별 도서 추천**를 추천합니다.
- 📖 추천도서 페이지에서는 **연령별 추천**과 사용자가 좋아하는 장르 중 **인기 있는 도서**를 추천합니다.




## 📊 ERD

![bookmaruERD.png](src%2Fmain%2Fresources%2Fstatic%2Fimgs%2FbookmaruERD.png)