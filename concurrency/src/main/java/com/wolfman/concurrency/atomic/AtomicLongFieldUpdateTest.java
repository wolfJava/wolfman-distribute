package com.wolfman.concurrency.atomic;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
public class AtomicLongFieldUpdateTest {
    public static void main(String[] args) {
        // 获取Person的class对象
        Class cls = PersonOne.class;
        // 新建AtomicLongFieldUpdater对象，传递参数是“class对象”和“long类型在类中对应的名称”
        AtomicLongFieldUpdater atomicLongFieldUpdater = AtomicLongFieldUpdater.newUpdater(cls,"id");
        PersonOne person = new PersonOne(12345678L);

        // 比较person的"id"属性，如果id的值为12345678L，则设置为1000。
        atomicLongFieldUpdater.compareAndSet(person, 12345678L, 1000);
        System.out.println("id="+person.getId());
        //id=1000
    }
}
class PersonOne {
    volatile long id;
    public PersonOne(long id) {
        this.id = id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
}