
package com.openclassrooms.entrevoisins.neighbour_list;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;





import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }


    //TODO: faire un test pour verifier lajout d'un voisin ?
    @Test
    public void myNeighbourList_shouldCreateItem() throws InterruptedException {

        //on vérifie que tout les voisins sont chargés
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));

        // on clique sur le btn ajouter
        onView(ViewMatchers.withId(R.id.add_neighbour)).perform(click());

        //on verifie que l'activity add neighbour est bien visible
        onView(withId(R.id.add_neighbour_activity)).check(matches(isDisplayed()));

        //on entre les données (name , phone number, address, aboutme)
        String name = "testname";
        String phoneNumber = "00000000";
        String address = "00 test";
        String aboutMe = "description test";
        onView(withId(R.id.name)).perform(typeText(name));
        onView(withId(R.id.phoneNumber)).perform(typeText(phoneNumber));
        onView(withId(R.id.address)).perform(typeText(address));
        onView(withId(R.id.aboutMe)).perform(typeText(aboutMe));
        //TODO: pourquoi ca bloque ici ?

        //on clique sur save
        onView(ViewMatchers.withId(R.id.create)).perform(click());

        //on verifie que le nombre de voisin est bien incrementé
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT+1));

    }


    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void myNeighbourList_onClick_shouldOpenDetailActivity(){
        // Cliquez sur l'élément à la position 0 (ou tout autre index que vous souhaitez tester).
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Vérifiez que l'activité de détail du voisin est bien ouverte.
        onView(withId(R.id.detail_activity)).check(matches(isDisplayed()));
    }

    @Test
    public void NeighbourDetail_shouldDisplayCorrectData(){
        // Cliquez sur l'élément à la position 0.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Vérifiez que l'activité de détail du voisin est bien ouverte.
        onView(withId(R.id.detail_activity)).check(matches(isDisplayed()));

        // Vérifier que les valeurs affichés correspond bien aux valeurs attendues

        int idToCompare = 1;
        //TODO: comment comparer un id quand il sagit dun int non visible sur la page ?
        //onView(withId(R.id.)).check(matches(withText(String.valueOf(idToCompare)));

        NeighbourApiService mApiService;
        mApiService = DI.getNeighbourApiService();

        Neighbour neighbourToCompare = mApiService.getNeighbour("0");

        String nameToCompare = neighbourToCompare.getName();
        onView(withText(nameToCompare)).check(matches(isDisplayed()));

        String urlToCompare = neighbourToCompare.getAvatarUrl();
        //TODO: comment faire quand il sagit de l'url d'une image ?

        String addressesToCompare = neighbourToCompare.getAddress();
        onView(withText(addressesToCompare)).check(matches(isDisplayed()));

        String phoneToCompare = neighbourToCompare.getPhoneNumber();
        onView(withText(phoneToCompare)).check(matches(isDisplayed()));

        String websiteToCompare = "www.facebook.fr/" + nameToCompare.toLowerCase();
        onView(withText(websiteToCompare)).check(matches(isDisplayed()));

        String aboutMeToCompare = neighbourToCompare.getAboutMe();
        onView(withText(aboutMeToCompare)).check(matches(isDisplayed()));
    }

    @Test
    public void tabFavoriteDisplayOnlyFavorite(){
        //TODO: faire le test pour verifier que l'onglet favoris affiche bien uniquement les favoris


    }

}