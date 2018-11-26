import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';
admin.initializeApp();

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

exports.getChats = functions.https.onCall((data, context) => {
    const userID = data.id;
    if (!(typeof userID === 'string') || userID.length === 0) {
        // Throwing an HttpsError so that the client gets the error details.
        throw new functions.https.HttpsError('invalid-argument', 'The function must be called with ' +
            'one argument "userID" containing the userID to query.');
    }
      // Checking that the user is authenticated.
    if (!context.auth) {
    // Throwing an HttpsError so that the client gets the error details.
    throw new functions.https.HttpsError('failed-precondition', 'The function must be called ' +
        'while authenticated.');
    }

    let retList: GroupMsgPreview[];

    const ref = admin.database().ref()
    ref.child('users/' + userID + '/group_ids').once('value', function(snapshot){
        snapshot.forEach(function(childSnapshot) {
            const currGroupID = childSnapshot.val()
            let currGroup: GroupMsgPreview;

            console.log(currGroupID)

            ref.child('groups/' + currGroupID).once('value', function(groupSnapshot){
                const adminName = groupSnapshot.val().admin
                const groupName = groupSnapshot.val().name
                
                currGroup.groupName = groupName
                currGroup.groupMsgPreview = adminName

                console.log(adminName + " is the admin of " + groupName)
            }).catch(() => 'obligatory catch')

            retList.push(currGroup)
            return false
        })
    }).catch(() => 'obligatory catch')

    console.log(retList)
});

interface GroupMsgPreview {
    groupName: string,
    groupMsgPreview: string
}


exports.sendNotification = functions.database.ref('groups/{group_id}/chat_log')
                            .onWrite((snapshot : any, context) => {
                                const newMessage = snapshot.after()

                                const payload = {
                                      notification: {
                                        title: 'You have a new follower!',
                                        body: `Hey is now following you.`
                                      }
                                    };

                                return admin.messaging().sendToDevice("ed_GTiKke7c:APA91bH8FI8Or_djpznhhfkN4-jiNfVNKryyAxBP4efXDgPe5Wek_dfT2wJcjrHhdwe3JbD5BRy45nJo9e4giLMUuPyS0dm2P47ugVBFiinD_lGfkB_a7UrqfewGFXV8eQy4ZiE0IdYY",
                                    payload)

                            })
