#EventBus Introdution && How to use

**EventBus:**
- [About the Introdution](https://greenrobot.github.io/EventBus/)
- [Projcte in GitHub](https://github.com/greenrobot/EventBus)
- [EventBus Documentation](http://greenrobot.org/eventbus/documentation/)

>**Introdution** : EventBus is a publish/subscribe event bus optimized for Android.
>
>**EventBus in 3 steps**
>
>**Define events:**
>```java
>public static class MessageEvent { /* Additional fields if needed */ }
>Prepare subscribers: Declare and annotate your subscribing method, optionally >specify a thread mode:
>```
>```java
>@Subscribe(threadMode = ThreadMode.MAIN)  
>public void onMessageEvent(MessageEvent event) {/* Do something */};
>Register and unregister your subscriber. For example on Android, activities and >fragments should usually register according to their life cycle:
>```
>```java
>@Override
>public void onStart() {
>    super.onStart();
>    EventBus.getDefault().register(this);
>}
>```
>```java
>@Override
>public void onStop() {
>    super.onStop();
>    EventBus.getDefault().unregister(this);
>}
>```
>**Post events:**
>```java
>EventBus.getDefault().post(new MessageEvent());
>```

##Demo
- [GitHub Link](https://github.com/tavisWei/DailyDemo/tree/master/EventBusDemo)

**Summary in this demo:**
1. When you use :
```java
EventBus.getDefault().post(new MessageEvent());
```
to post your event,received method only have ``one`` parameter so that the post method have ``one`` parameter too.

2. When your Activity finish but you don't unregister EventBus, the Activity will not recover and still receiving the registering method.If you mark your receiving method in UI Main thread,it will occupy your UI Main thread resource.Of course,if your view detached from window,it will not deal with in your view.
3. In the receiving method,EventBus had added try/catch protection mechanism,if you attemp to change UI information in ThreadMode.BACKGROUND or ThreadMode.ASYNC,not will be crash,but it will not refresh ui in lower than Android 5.0 (you must do in UI thread better).
4. you can set the priority which thread deal with cpu ``@Subscribe(threadMode = ThreadMode.BACKGROUND, priority = 0)`` 
5. if you want to post your command than don't act at time, you can use ``EventBus.getDefault().postSticky()``
For example:
```java
EventBus.getDefault().postSticky(new MessageMode("Sticky MessageMode"));
```
When you go back to other Activity
```java
    @Override
    protected void onStart() {
        super.onStart();

        MessageMode mMessageMode = EventBus.getDefault().getStickyEvent(MessageMode.class);
        if (mMessageMode != null) {
            getMessageModeToast(mMessageMode);
            EventBus.getDefault().removeStickyEvent(MessageMode.class);
        }
    }
```
you can get your command in ``EventBus.getDefault().getStickyEvent()`` and then just do you wanna to do.
