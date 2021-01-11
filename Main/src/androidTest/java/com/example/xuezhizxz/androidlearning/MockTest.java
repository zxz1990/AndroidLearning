package com.example.xuezhizxz.androidlearning;

import com.android.dx.mockito.inline.extended.ExtendedMockito;
import com.example.zxz.androidtest.utils.AndroidTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoSession;
import org.mockito.internal.junit.JUnitRule;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static com.android.dx.mockito.inline.extended.ExtendedMockito.mockitoSession;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@LargeTest
//@RunWith(MockitoJUnitRunner.class)
@RunWith(AndroidJUnit4.class)
//@PrepareForTest({AndroidTestUtils.class})
public class MockTest {

  private static final String TAG = "HomeTabStoreAndroidTest";


  //  @Rule
  //  public MockitoRule rule = MockitoJUnit.rule().silent();


  //  @Rule
  //  public PowerMockRule rule = new PowerMockRule();

  //  @Mock
  //  ViewModelProvider mViewModelProvider;

  @Before
  public void setUpBefore() {
  }

  @After
  public void setUpAfter() {
  }

  @Test
  public void testStatic() {
    System.out.println("testStatic");
    //    PowerMockito.mockStatic(AndroidTestUtils.class);
    //    Mockito.when(AndroidTestUtils.getString()).thenReturn("mock的数据");
    //    Log.i("ZXZ", "mock结束");

    MockitoSession session =
        ExtendedMockito.mockitoSession().spyStatic(AndroidTestUtils.class).startMocking();
    try {
      // By default all static methods of the mocked class should return the default answers
      //      assertNull(AndroidTestUtils.getString());

      // Super class is not mocked
      System.out.println("testStatic1: str1=" + AndroidTestUtils.getString1() + "，str2=" + AndroidTestUtils.getString2());
      when(AndroidTestUtils.getString1()).thenReturn("fake1");
      System.out.println("testStatic2: str1=" + AndroidTestUtils.getString1() + "，str2=" + AndroidTestUtils.getString2());

      // Make sure behavior is changed

      // Super class should not be affected
    } finally {
      session.finishMocking();
    }

  }


}
