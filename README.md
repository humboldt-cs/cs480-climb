# Climb
## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
This is an app for the outdoorsperson in all of us. It allows the user to explore outdoor destinations via pins on a map and to view pictures associated with each location so the user to pick a spot to travel to. The application will allow outdoorspeople to communicate with friends and plan group trips.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Outdoor/social 
- **Mobile:** Only available for mobile devices.
- **Story:** Allows users to view and post new climbs and interact with other users. 
- **Market:** Climbers who like to climb outside. 
- **Habit:** Users can search climbs and plan trips ahead of time, as well as checking the feed for daily updates from their 
  area and friends. 
- **Scope:** Initially just an app for storing information about climbs, Climb has expanded to allow climbers to network and 
  make new climbing friends. 

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**
* User can log in or sign up.  
* User can view a map that is populated with pins set by other users.
* User can create pins and routes. 
* User can tap on pins to view photos and other details of the associated location.
* User is able to create a "trip" which their friends may sign up for to indicate interest in going.
* Users have a profile that other users can view. 

**Optional Nice-to-have Stories**
* Users can friend/unfriend other users.
* User may choose a road trip destination and get recomendations for hiking/climbing spots near the calculated route.
* Users can see a feed with their friends achievements, climbs that match their interests and location, new routes and pins, 
  and trips that are set to public or that they have been invited to view. 
* Users can send messages to other users including friends. 
* The user's profile includes their activity including recent trips, routes created, achievements, and pictures/videos posted
  by them. 
* User can add comments to trips and routes. 
 

### 2. Screen Archetypes

* Login 
   * User can login
* Sign up 
   * User can create a new account (required info: handle, display name, email, password, confirm password)
* Forgot your password?
   * User enters their email and an email is sent with instructions to recover their password. 
* Password recovery
   * After following the link from their email, the user can create and confirm a new password. 
* Feed
   * Users can view their personalized feed. The feed shows a toast if new posts are added and the user can swipe to refresh. 
* Map 
   * Uses the googlemaps api to display a map with pins containing route(s). 
   * Has a search bar at the top to look up pins and routes. 
   * Users can place the pin or add a route to a an existing pin. 
* New route 
   * After placing the pin/selecting existing pin, the user can add details about a new route
* Profile
   * Displays a profile picture and short description
   * Other users can send a message, invite them to a trip, and see their feed and location if allowed
* Private messages
    * User can view messages sent to them and reply


### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Map 
* Feed
* Profile

**Flow Navigation** (Screen to Screen)

* Login  
   * Sign up 
   * Forgot password?
   * Map 
* Map 
   * Private messages
   * New route 
 * Feed - none
 * Profile 
   * Private messages
   * Trip invite 
   * Trip creation

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://github.com/mmmz21/Climb/blob/master/wireframe.jpg" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
