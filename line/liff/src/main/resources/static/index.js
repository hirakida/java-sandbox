window.onload = () => {
    os.innerText = liff.getOS();
    language.innerText = liff.getLanguage();
    version.innerText = liff.getVersion();
    lineVersion.innerText = liff.getLineVersion();
    inClient.innerText = liff.isInClient();

    liff.init({
        liffId: document.getElementsByName('liffId')[0].content
    }).then(() => {
            loggedIn.innerText = liff.isLoggedIn();
            context.innerText = JSON.stringify(liff.getContext());

            loginBtn.onclick = () => {
                if (!liff.isLoggedIn()) {
                    liff.login();
                }
            };

            closeBtn.onclick = () => {
                liff.closeWindow();
            };

            profileBtn.onclick = () => {
                liff.getProfile()
                    .then(data => {
                        userId.innerText = data.userId;
                        displayName.innerText = data.displayName;
                        pictureUrl.src = data.pictureUrl;
                        statusMessage.innerText = data.statusMessage;
                    })
                    .catch(err => {
                        console.error(err);
                    });
            };

            sendTextBtn.onclick = () => {
                liff.sendMessages([{
                    type: 'text',
                    text: textMessage.value
                }]).then(() => {
                    console.log('sent');
                }).catch((err) => {
                    console.log(err);
                    sendError.innerText = JSON.stringify(err);
                });
            };
        },
        err => {
            console.error(err);
        });
};
