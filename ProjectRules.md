### **GLOBAL PROJECT RULES — ANDROID TAG-BASED MEDIA STORE APP - AnixDataStore**

#### **1. Agent Role and Operating Mode**

1.1 Act as a **long-term Android project AI agent** responsible for planning, modifying, and extending this application.
1.2 Assume **stateful continuity**: all discussions, files, and decisions belong to the same project unless explicitly reset.
1.3 Never treat any request as isolated. Always reconcile it with:
1.3.a Existing requirements
1.3.b Current project structure
1.3.c Previously discussed design decisions
1.3.d Provided sample files

---

#### **2. Core Application Objective**

2.1 Build an Android application that functions as a **tag-driven data store**.
2.2 The system must manage three primary data types:
2.2.a Movie Stars
2.2.b Video Links
2.2.c Playlists
2.3 Tags are **first-class citizens** and must drive:
2.3.a Search
2.3.b Filtering
2.3.c Data relationships

---

#### **3. Tag System Rules**

3.1 Every Star and Link must support **multiple tags**.
3.2 Tags must be:
3.2.a Structured into sub-categories
3.2.b Searchable
3.2.c Extendable in the future
3.3 Tags can be:
3.3.a Mandatory (enforced at creation)
3.3.b Optional (added later)
3.4 Filtering must always be **intersection-based** (AND logic unless explicitly stated otherwise).

---

#### **4. Screen Architecture Rules**

4.1 The application must have the following primary screens:
4.1.a Main Screen
4.1.b Stars Screen
4.1.c Links Screen
4.1.d Playlists Screen

---

#### **5. Main Screen Rules**

5.1 The Main Screen must contain:
5.1.a A filters section
5.1.b A combined results list
5.2 Filters applied on the Main Screen must return:
5.2.a Stars matching the selected tags
5.2.b Links matching the selected tags
5.3 Results must clearly distinguish Stars vs Links.
5.4 An **Add button** must exist.
5.5 On Add action:
5.5.a Ask whether the user wants to add a Star or a Link
5.5.b Route to the respective Add screen

---

#### **6. Navigation Drawer Rules**

6.1 A sliding drawer must always contain exactly three sections:
6.1.a Stars
6.1.b Links
6.1.c Playlists
6.2 Navigation must preserve:
6.2.a Applied filters (where relevant)
6.2.b User context

---

#### **7. Stars Screen Rules**

7.1 The Stars Screen must:
7.1.a Mirror Main Screen filtering behavior
7.1.b Show **only Stars**
7.2 Display **star-specific tags only**, including but not limited to:
7.2.a Region
7.2.b Language
7.2.c Age group
7.2.d Hair color
7.2.e Genres
7.3 Selecting tags must progressively narrow the list.
7.4 Must include an **Add Star** button.

---

#### **8. Links Screen Rules**

8.1 The Links Screen must:
8.1.a Mirror Main Screen filtering behavior
8.1.b Show **only Links**
8.2 Display **link-specific tags only**, including but not limited to:
8.2.a Title
8.2.b Actor names
8.2.c Genres
8.2.d Actor descriptions
8.2.e Video duration (e.g., >30 min)
8.3 Selecting tags must progressively narrow the list.
8.4 Must include an **Add Link** button.

---

#### **9. Playlists Screen Rules**

9.1 The Playlists Screen must:
9.1.a Not contain filters
9.1.b Support sorting only by:
9.1.b.i Date
9.1.b.ii Favorites
9.2 A Playlist is a **collection of Links**.
9.3 An **Add button** must allow:
9.3.a Creating a new playlist
9.3.b Adding links to an existing playlist
9.3.c Creating sub-playlists within a playlist

---

#### **10. Add Star Rules**

10.1 Mandatory fields:
10.1.a Name
10.1.b At least one Genre
10.1.c Hair color
10.1.d Language
10.2 Tag selection must be:
10.2.a Category-based
10.2.b Dropdown-driven
10.2.c Easily extensible

---

#### **11. Add Link Rules**

11.1 Mandatory fields:
11.1.a Title
11.1.b Actor name(s)
11.1.c At least one Genre
11.1.d Language
11.2 Tag selection must follow the same structured dropdown model as Stars.

---

#### **12. Add Playlist Rules**

12.1 First decision must be:
12.1.a Add Playlist
12.1.b Add Link to Playlist
12.2 If Playlist:
12.2.a Ask for title only
12.3 If Link:
12.3.a Display all existing links
12.3.b Preserve the same ordering as the Links Screen
12.3.c Allow selection and confirmation

---

#### **13. Data & Architecture Constraints**

13.1 Assume future scalability.
13.2 Avoid hard-coded tag logic.
13.3 Prefer reusable filter and tag components.
13.4 UI, data models, and filtering logic must remain **loosely coupled**.

---

#### **14. Agent Behavior Constraints**

14.1 Never remove an existing feature unless explicitly instructed.
14.2 Always propose a plan before modifying sample files.
14.3 When ambiguity exists:
14.3.a Ask clarifying questions
14.3.b Do not make silent assumptions
14.4 Keep all outputs structured and implementation-ready.

---

#### **15. Success Definition**

15.1 The app must allow:
15.1.a Flexible tagging
15.1.b Accurate filtering
15.1.c Clean separation of Stars, Links, and Playlists
15.2 The design must remain extensible without rework.

---

**End of Rules.**
