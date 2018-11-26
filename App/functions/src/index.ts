import * as functions from 'firebase-functions';
import * as admin from 'firebase-admin';
admin.initializeApp();

// Start writing Firebase Functions
// https://firebase.google.com/docs/functions/typescript

exports.getChats = functions.https.onRequest((req, res) => {
    const userID = req.query.id
    
    if (!(typeof userID === 'string') || userID.length === 0) {
        // Throwing an HttpsError so that the client gets the error details.
        throw new functions.https.HttpsError('invalid-argument', 'The function must be called with ' +
            'one argument "userID" containing the userID to query.');
    }


    const ref = admin.database().ref()
    ref.child('users/' + userID + '/group_ids').once('value', groupIdsSnapshot => {
        const snap = groupIdsSnapshot.val()
        console.log(snap)
        console.log(snap, ' is here!!!!!!')
        // res.status(200).send(snap);
        res.status(200).send(Object.keys(snap).map((key) => snap[key]));
    })
    .catch((err: Error) => {
        throw err;
    })
});

exports.getGroupInfo = functions.https.onRequest((req, res) => {
    const groupId = req.query.id
    
    if (!(typeof groupId === 'string') || groupId.length === 0) {
        // Throwing an HttpsError so that the client gets the error details.
        throw new functions.https.HttpsError('invalid-argument', 'The function must be called with ' +
            'one argument "userID" containing the userID to query.');
    }

    const ref = admin.database().ref()
    ref.child('groups/' + groupId).once('value', groupDatasSnapshot => {
        const groupName = groupDatasSnapshot.val().groupName
        const groupMsgPreview = groupDatasSnapshot.val().groupMsgPreview

        let currGroup: GroupMsgPreview = {
            groupName,
            groupMsgPreview,
            groupId
        }
        res.status(200).send(currGroup)
    })
    .catch((err: Error) => {
        throw err;
    })
});

interface GroupMsgPreview {
    groupName: String,
    groupMsgPreview: String,
    groupId: String
}

exports.setTextPreview = functions.database.ref('groups/{groupId}/messages/{messageId}').onCreate((snapshot, context) => {
    const groupId = context.params.groupId
    console.log(groupId)
    
    const messageId = context.params.messageId
    console.log(messageId)

    console.log(snapshot.val())

    const text = snapshot.val().text
    console.log(text)

    const ref = admin.database().ref('groups/' + groupId)
    return ref.update({"preview": text})
})