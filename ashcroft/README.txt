
Having a unit test suite that is slow is very annoying. Anything that takes more than 10 seconds is slow. Worse, having a test suite that is more than slow is not only annoying, it is also painful. Anything that takes more than a minute to run is painful.

If a test suite takes 10 minutes to run, you can in theory run it six times an hour. However, because most humans will do what they can to avoid pain, they will typically not run such a test suite more than 6 times a day.

execution interval = execution time + C * execution time ^ 2

In other words, if you want unit tests to run often, you better keep them very very fast.

So why is it so important to have a fast running test suite then? This really depends on your development process. If you are developing according to a waterfall-like approach, then it probably doesn't make a big difference for you how fast the tests are. Your process doesn't stress running tests frequently in the first place.

However, if you are developing according to an agile process such as XP, perhaps trying to follow the principles of TDD, refactoring and continuous integration, then you are in serious trouble if your tests are slow. Simply because all of these practices are based on frequent execution of tests. If the core practices in your process are inhibited (in this case by slow tests) you and your team will accumulate frustration. Frustration is bad.

There are ways to adress this frustration - simply by making the tests run fast as a lightning. The rest of this article will focus on tools and techniques that you can follow to achieve this.

Before we start looking at explicit techniques to make tests run fast, let's have a look at the typical caracteristics of a slow running tests. Things usually start grinding into a halt when the following things happen:

o Files are accessed
o Sockets are opened
o Threads are spawned
o GUI widgets are displayed

Common for all these operations is that they are related to technical infrastructure. Every class that implements business logic and depends on infrastructure at the same time can not be tested in isolation. This means that in order to test this business logic, you also have to deal with an external environment that is likely to severely slow down your tests.

In addition to being cumbersome to test in isolation, classes that deal with business logic and infrastructure at the same time are also candidates for refactoring. They have too many (unrelated) responsibilities .

The solution is to decouple all business logic from the infrastructure. A trivial example: Account that sends email when it is overdraft.
* Show an initial, trivial example
* Decouple it by introducing delegation, di and mocks.

While at a project with ThoughtWorks I was introduced to a codebase with a test suite that took 17 minutes to run. It is easy to figure out what tests are slow by looking at JUnit's logs. It takes a bit more investigation to find out why tests are slow.

Knowing what makes tests slow, Obie and I were discussing how we could provide an automatic way to prevent less experienced (and also seasoned) developers from writing code that is hard to test fast (or tests that themselves are slow). We wanted a tool that could plug easily and transparently into any JUnit runner and make tests that do "slow" and "bad" operations fail immediately with a descriptive explanation. We quickly agreed to spike some code and ended up with a java security manager that fulfilled all of our requirements. Every time the code attempts to do something that is considered harmful to test speed (and in some cases - harmful to test sanity in general), the security manager throws a CantDoThat error.

We thought John Ashcroft would be a good name for this tool.

Conclusion:

Remember that unit tests are intended to test _units_. There are many ways to interpret what a unit means, but I like to think of a unit as a single class minus all its dependencies.
