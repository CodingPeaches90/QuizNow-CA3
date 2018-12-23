package com.appone.jordan.quiznow;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutPageActivity extends AppCompatActivity
{
    /**
     * This Activity display's our About Page. It uses an external API
     * call to make our about page!
     */

    private String getDescription;
    private String getFacebook;
    private String getTwitter;
    private String getVersion;
    private String getEmail;
    private String getPlaystore;
    private String getYoutube;
    private String getSocial;
    private String getWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Element adsElement = new Element();
        adsElement.setTitle("Advertise!");

        getDescription = getString(R.string.quiznow_description);
        getFacebook = getString(R.string.quiznow_facebook);
        getTwitter = getString(R.string.quiznow_twitter);
        getVersion = getString(R.string.quiznow_version);
        getEmail = getString(R.string.quiznow_email);
        getPlaystore = getString(R.string.quiznow_playstore);
        getYoutube = getString(R.string.quiznow_youtube);
        getSocial = getString(R.string.quiznow_social);
        getWebsite = getString(R.string.quiznow_website);

        // About page object
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.resized_logo)
                .setDescription(getDescription)
                .addItem(new Element().setTitle(getVersion))
                .addItem(adsElement)
                .addGroup(getSocial)
                .addEmail(getEmail)
                .addWebsite(getWebsite)
                .addFacebook(getFacebook)
                .addTwitter(getTwitter)
                .addYoutube(getYoutube)
                .addPlayStore(getPlaystore)
                .create();

        setContentView(aboutPage);

    }
}
