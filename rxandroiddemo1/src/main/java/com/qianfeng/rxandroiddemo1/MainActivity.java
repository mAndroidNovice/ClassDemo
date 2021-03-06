package com.qianfeng.rxandroiddemo1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * RxAndroid 简单试讲第一天
 * 本质是基于观察者模式的
 * 观察者,被观察者, 订阅的方式建立联系
 */
public class MainActivity extends AppCompatActivity {

    private Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(R.id.bt1);
//        bt1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("自定义标签", "类名==MainActivity" + "方法名==onClick=====:" + "");
//            }
//        });
        //v代表形参的名字 -> 后面跟上具体要做的事情
//        bt1.setOnClickListener(v-> Log.e("自定义标签", "类名==MainActivity" + "方法名==onCreate=====:" + ""));
        // observer();
//        subscriber();
        // action1();
//        range();
        //just();
//        defer();
        //from();
//        fromList();
        //  interval();
//        repeat();
//        flatMap();
//        flatMapIterable();
//        map();
//        cast();
    }

    private void cast() {//直接类型的转换,和 map 的区别是, map是通过某种方式将一种类型转成另外一种类型,而 cast则是通过 java
        //的强制类型转换变成对应的类型
        GuanChaZhe guanChaZhe = new G1();
        Observable.just(guanChaZhe).cast(G1.class).subscribe(new Action1<G1>() {
            @Override
            public void call(G1 g1) {
                g1.onChange();
            }
        });
    }

    private void map() {
        Observable.just(1, 2, 3, 4).map(integer -> integer + "字符串").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("自定义标签", "call() called with: " + "s = [" + s + "]");
            }
        });
    }

    private void flatMapIterable() {//转换为可以迭代的数据,转换后数据会重复执行多次
        Func1<Integer, List<Integer>> func1 = new Func1<Integer, List<Integer>>() {//泛型1 为原始类型,泛型2为转换后的类型
            ArrayList<Integer> arrayList = new ArrayList<>();

            @Override
            public List<Integer> call(Integer integer) {
                arrayList.add(integer);
                return arrayList;
            }
        };

        Observable<Integer> observable = (Observable<Integer>) Observable.just(1, 2, 3, 4, 5, 6).flatMapIterable(func1).subscribe(integer -> Log.e("自定义标签", "类名==MainActivity" + "方法名==flatMapIterable=====:" + integer));

    }

    private void flatMap() {//可以进行类型转换
        Observable<Integer> observable = Observable.just(1, 2, 3, 4);
        Observable<String> observable1 = observable.flatMap(integer -> Observable.just("转换" + integer));
        observable1.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("自定义标签", "call() called with: " + "s = [" + s + "]");
            }
        });
    }

    private void repeat() {//有限循环,将制定的内容循环发送指定的次数
        Observable<Integer> observable = (Observable<Integer>) Observable.just(1, 2, 3, 4).repeat(10).subscribe(integer -> Log.e("自定义标签", "类名==MainActivity" + "方法名==repeat=====:" + integer));

    }

    private void interval() {//对外发送数字, 每个多久发送一次,无限循环
        Observable<Integer> observable = (Observable<Integer>) Observable.interval(3, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {

                Log.e("自定义标签", "类名==MainActivity" + "方法名==call=====:" + aLong);
            }
        });
    }

    private void fromList() {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stringList.add("" + i);
        }
        Observable<String> observable = (Observable<String>) Observable.from(stringList).subscribe(s -> Log.e("自定义标签", "类名==MainActivity" + "方法名==fromList=====:" + s));

    }

    private void from() {//讲数组或者是可迭代的对象内容取出来一个一个发射出来
        String[] strings = {"1", "2", "3", "4", "fda", "rerwe"};
        Observable.from(strings).subscribe(s -> Log.e("自定义标签", "类名==MainActivity" + "方法名==from=====:" + s));
    }

    private void defer() {//延迟操作,只有创建订阅者并且订阅之后才会创建Observable
//        Func0<String> func0 = new Func0() {
//            @Override
//            public String call() {
//                return "!1111111";
//            }
//        };
        Observable.defer(() -> Observable.just(1, 2, 3, 4)).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e("自定义标签", "call() called with: " + "integer = [" + integer + "]");
            }
        });
    }

    private void just() {//将 just里面的参数一个一个拿出来之后对外发射, just 内部可以是数字 字符串等任意内容
        Observable<String> observable = (Observable<String>) Observable.just("1", "a", "c", "Fsfsadf", "ß˙©®¡ª•¶º¡¡•µ¥øµµå").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("自定义标签", "call() called with: " + "s = [" + s + "]");
            }
        });
    }

    private void range() {//从 start开始,执行 count次循环,发送出去的数据也是从 start 开始,依次增加 count   lambda 表达式
        Observable<Integer> observable = (Observable<Integer>) Observable.range(5, 5).subscribe(integer -> Log.e("自定义标签", "类名==MainActivity" + "方法名==range=====:" + integer));
//        Observable<Integer> observable1 = (Observable<Integer>) Observable.range(5,5).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                Log.e("自定义标签", "类名==MainActivity" + "方法名==call=====:" + integer);
//            }
//        });
    }

    private void action1() {
        Observable<String> observable = (Observable<String>) Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("action1的方式触发了");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("自定义标签", "call() called with: " + "s = [" + s + "]");
            }
        });
    }

    private void subscriber() {
        Observable<String> mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {//参数就是订阅者
                //被观察的数据发生变化的时候发射
                if (!subscriber.isUnsubscribed()) {//如果仍处于订阅状态,才会通知观察者
                    subscriber.onNext("数据发生变化了");
                }
            }
        });
        mObservable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String o) {
                Log.e("自定义标签", "onNext() called with:subscriber " + "o = [" + o + "]");
            }
        });
    }

    private void observer() {
        Observable<String> mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {//参数就是订阅者
                //被观察的数据发生变化的时候发射
                if (!subscriber.isUnsubscribed()) {//如果仍处于订阅状态,才会通知观察者
                    subscriber.onNext("数据发生变化了");
                }
            }
        });
        mObservable.subscribe(new Observer<String>() {//建立订阅
            @Override
            public void onCompleted() {//完成

            }

            @Override
            public void onError(Throwable e) {//出现错误

            }

            @Override
            public void onNext(String o) {//正在执行
                Log.e("自定义标签", "onNext() called with: " + "o = [" + o + "]");
            }
        });
    }
}
