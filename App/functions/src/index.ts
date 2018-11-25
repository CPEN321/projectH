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
