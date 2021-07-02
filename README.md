# 키친포스

## 요구 사항

**메뉴 그룹 관리**
- [ ] 메뉴 그룹을 생성한다.
- [ ] 메뉴 그룹 리스트를 조회한다.
----

**메뉴 관리**
- [ ] 메뉴를 생성한다.
  - [ ] 메뉴의 가격은 Null 일 수 없다.
  - [ ] 메뉴의 가격은 0보다 작을 수 없다.
  - [ ] 메뉴의 메뉴 그룹에 속해야만 한다.
  - [ ] 메뉴의 메뉴 상품 리스트를 조회할 수 있어야 한다.
  - [ ] 메뉴 가격보다 메뉴의 메뉴 상품 리스트의 가격의 합이 크면 안된다.
  - [ ] 메뉴에 메뉴 상품들을 등록한다.
- [ ] 메뉴 리스트를 조회한다.
  - [ ] 메뉴와 메뉴 상품 리스트를 함께 조회한다.
----

**주문 관리**
- [ ] 주문을 생성한다.
  - [ ] 주문의 주문 항목이 1개 이상이어야 한다.
  - [ ] 주문의 주문 항목들이 메뉴에 존재하지 않으면 생성할 수 없다.
  - [ ] 주문의 주문 테이블이 존재하지 않거나 빈 테이블이 있으면 생성할 수 없다.
  - [ ] 주문을 등록할 때 조리 상태로 등록한다.
  - [ ] 주문을 등록할 때 주문 테이블, 주문 항목을 함께 저장한다.
- [ ] 주문 리스트를 조회한다.
  - [ ] 주문과 주문의 속한 주문 항목들도 함께 조회한다.
- [ ] 주문 상태를 조회한다.
  - [ ] 계산 완료된 상태의 주문은 에러를 발생한다.
  - [ ] 주문과 주문 항목, 주문 상태를 조회한다.
----

**주문 상태**
- [ ] 주문의 상태는 조리, 식사, 계산 완료 상태가 있다.
----

**상품 관리**
- [ ] 상품을 생성한다.
  - [ ] 상품의 가격이 Null이 아니고 0원 초과하여야 한다.
  - [ ] 상품을 저장한다.
- [ ] 상품 리스트를 조회한다.
----

**단체 지정 관리**
- [ ] 단체 지정 생성한다.
  - [ ] 주문 테이블이 빈 테이블이거나, 2개 미만인 주문 테이블은 단체 지정에 등록될 수 없다.
  - [ ] 주문 테이블이 존재하지 않으면 에러를 발생한다.
  - [ ] 주문 테이블이 손님이 비워져 있거나 이미 단체지정에 속하면 안된다.
  - [ ] 단체 지정에 주문 테이블들을 등록한다.
- [ ] 단체 지정 해체한다
  - [ ] 주문 테이블들이 조리중이거나 식사중일 경우 단체 지정을 해체할 수 없다.
  - [ ] 주문 테이블들의 단체 지정을 해체한다.

----

**주문 테이블 관리**
- [ ] 주문 테이블 생성한다
  - [ ] 단체지정이 비워져 있는 상태로 등록한다.
- [ ] 주문 테이블 리스트를 조회한다.
- [ ] 주문 테이블을 비운다.
  - [ ] 비울 주문 테이블이 존재하지 않으면 할 수 없다.
  - [ ] 비울 주문 테이블이 단체 지정에 속하면 비울 수 없다.
  - [ ] 비울 주문 테이블들이 조리중이거나 식사중일 경우 비울 수 없다.
- [ ] 주문 테이블의 손님 수를 변경한다.
  - [ ] 변경할 주문 테이블의 손님의 수가 0명보다 작은 경우 변경할 수 없다.
  - [ ] 변경할 주문 테이블이 존재하지 않으면 변경할 수 없다.
  - [ ] 주문 테이블의 손님 수를 변경하여 저장한다.

## 용어 사전
----

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
