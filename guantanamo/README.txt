Do you have problems keeping test coverage up? Send the untested code to Guantanamo!

Guantanamo is a tool that comments out all code that is not covered by tests. Guantanamo can also
be run in extreme mode, where it deletes untested code.

I hear you say "but what if the removed code actually serves a good purpose?". The answer is simple.
You can't prove it! According to Guantanamo, all code is guilty until proven innocent. Innocence is
proven by test coverage.

The best way to use Guantanamo is:
1) Run your tests with the code coverage tool* enabled.
2) Run guantanamo and write the guantanamoed code to a different folder, say guantanamo.
3) Compile the guantanamoed code.
4) Repeat step 1), but this time running against the guantanamoed code.
5) Copy the guantanamoed code over the original code.

Step 3 may fail if Guantanamo removed so much code that the code fails to compile. Tough luck.
Step 4 may fail too. Too bad.
So, you may have to do some tidying up.
When you get past 3) and 4) and eventually 5) you should have 100% test coverage.

*Guantanamo currently works in conjunction with Clover. Other code coverage engines may be supported
in the future (we gladly accept patches).

Guantanamo doesn't have 100% test coverage. Why isn't Guantanamo's own untested code deleted?
We'll answer that if you can answer this:

"Various US presidents have started wars. Why didn't any of these presidents' children have to serve
the US army in any of these wars?"

If you like the idea of Guantanamo, you may also like Ashcroft, another tool in the "Extreme XP tools"
family.