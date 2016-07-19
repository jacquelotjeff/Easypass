casper.options.viewportSize = {width: 1600, height: 950};
casper.test.begin('Acess permission testing', 5, function suite(test) {

    casper.start("http://localhost:8080/easypass", function() {

        test.assertTitle("Easypass - Accueil", "Easypass home page has correct title.");

    }).thenOpen('http://localhost:8080/easypass/admin/utilisateurs', function() {

        test.assertExists('div.alert.alert-warning', "Alert admin connexion is found.");
        test.assertSelectorHasText('div.alert.alert-warning', 'Message : Veuillez vous identifier.');

	}).thenOpen("http://localhost:8080/easypass/utilisateur", function(){

		test.assertExists('div.alert.alert-warning', "Alert user connexion is found.");
		test.assertSelectorHasText('div.alert.alert-warning', 'Message : Veuillez vous identifier.');

	});

	casper.run(function() {
    	test.done();
	});
});

casper.test.begin('Sign up testing', 5, function suite(test) {

    casper.start("http://localhost:8080/easypass", function() {
        test.assertTitle("Easypass - Accueil", "Easypass home page has correct title.");
    }).thenClick('a[href="inscription"]', function() {
        test.assertTitle("Easypass - Inscription", "Easypass sign up page has correct title.");
	    
	    this.fillSelectors('#form-sign-up', {
	        'input[name="username"]':    '',
	        'input[name="lastname"]':    '',
	        'input[name="firstname"]':   '',
	        'input[name="email"]':      '',
	        'input[name="password"]':   '',
    	}, false);
	    
	}).thenClick('#btn-sign-up', function(){

		test.assertElementCount('div.form-group.has-error', 5, "All sign up fields are validated");

		this.fillSelectors('#form-sign-up', {
	        'input[name="username"]':    'casperjs',
	        'input[name="lastname"]':    'Lastname',
	        'input[name="firstname"]':   'Firstname',
	        'input[name="email"]':      'casper@js.com',
	        'input[name="password"]':   'casperpassword',
    	}, false);

	}).thenClick('#btn-sign-up', function(){

		casper.wait(1000, function() {
	    	test.assertExists('div.alert.alert-success', "Casper user has successfully sign up.");
	    	test.assertSelectorHasText('div.alert.alert-success', 'Message : Vous êtes bien inscrit');
		});

	});

	casper.run(function() {
    	test.done();
	});

});

casper.test.begin('Sign in testing', 13, function suite(test) {

    casper.start("http://localhost:8080/easypass", function() {
        
        test.assertTitle("Easypass - Accueil", "Easypass home page has correct title.");

        this.fillSelectors('#form-sign-in', {
	        'input[name="email"]':    'jjacquelot@gmail.com',
	        'input[name="password"]':    'admin1234',
    	}, false);

    }).thenClick('#btn-sign-in', function(){

    	test.assertTitle("Easypass - Consulter le profil de jjacquelot");

    	casper.wait(1000, function(){

	    	test.assertExists('div.alert.alert-success', "Casper user has successfully sign in.");
	    	test.assertSelectorHasText('div.alert.alert-success', 'Message : Vous êtes bien connecté.');
	    	test.assertSelectorHasText('div.user-heading h3', 'jjacquelot')
	    	test.assertSelectorHasText('div.user-heading span.help-block', 'jjacquelot@gmail.com');
	    	test.assertSelectorHasText('div#information', 'Jeff');
	    	test.assertSelectorHasText('div#information', 'Jacquelot');
	    	test.assertSelectorHasText('div#information', 'jjacquelot@gmail.com');

    	});

	}).thenOpen("http://localhost:8080/easypass/admin/utilisateurs", function(){

		test.assertExists('div.alert.alert-warning', "Alert no admin access is found.");
		test.assertSelectorHasText('div.alert.alert-warning', 'Message : Accès interdit');

	}).thenClick("#btn-logout", function(){
	}).thenOpen("http://localhost:8080/easypass/utilisateur", function(){
		
		test.assertExists('div.alert.alert-warning', "Alert user connexion is found.");
		test.assertSelectorHasText('div.alert.alert-warning', 'Message : Veuillez vous identifier.');

	});

	casper.run(function() {
    	test.done();
	});

});

casper.test.begin('User profile edition testing', 13, function suite(test) {

    casper.start("http://localhost:8080/easypass", function() {

    	this.fillSelectors('#form-sign-in', {
	        'input[name="email"]':    'jjacquelot@gmail.com',
	        'input[name="password"]':    'admin1234',
    	}, false);

	}).thenClick('#btn-sign-in', function(){

		test.assertTitle("Easypass - Consulter le profil de jjacquelot");

	}).thenClick('#btn-edit-profile', function(){

		test.assertSelectorHasText('div.col-sm-12>h3', 'Editer mon profil jjacquelot');
		
		test.assertEquals(this.getElementAttribute('#firstname', 'value'), 'Jeff', "The firstname is correctly displayed");
		test.assertEquals(this.getElementAttribute('#lastname', 'value'), 'Jacquelot', "The lastname is correctly displayed");
		test.assertEquals(this.getElementAttribute('#email', 'value'), 'jjacquelot@gmail.com', "The email is correctly displayed");

		this.fillSelectors('#edit-profile', {
	        '#lastname':    '',
	        '#firstname':   '',
	        '#email':      'jlkjlkj',
    	}, false);

	}).thenClick('#btn-edit-profile', function(){

		casper.wait(1000, function(){

			test.assertElementCount('div.form-group.has-error', 3, "All editing profile fields are validated");

			this.fillSelectors('#edit-profile', {
		        '#lastname':    'Martins',
		        '#firstname':   'Jeff',
		        '#email':      'jeff-martins@gmail.com',
	    	}, false);

		});

	}).thenClick('#btn-edit-profile', function(){

		casper.wait(1000, function(){
			test.assertExists('div.alert.alert-success', "Casper user has successfully sign up.");
	    	test.assertSelectorHasText('div.alert.alert-success', 'Message : L\'utilisateur a bien été édité.');
	    	test.assertSelectorHasText('div.user-heading h3', 'jjacquelot')
	    	test.assertSelectorHasText('div.user-heading span.help-block', 'jeff-martins@gmail.com');
	    	test.assertSelectorHasText('div#information', 'Jeff');
	    	test.assertSelectorHasText('div#information', 'Martins');
	    	test.assertSelectorHasText('div#information', 'jeff-martins@gmail.com');
		});

	}).thenClick('#btn-logout', function(){

		this.fillSelectors('#form-sign-in', {
	        'input[name="email"]':    'jeff-martins@gmail.com',
	        'input[name="password"]':    'user1234',
    	}, false);

	});

	casper.run(function() {
    	test.done();
	});

});

casper.test.begin('Adding personnal password test', 12, function suite(test) {

    casper.start("http://localhost:8080/easypass", function() {

    	this.fillSelectors('#form-sign-in', {
	        '#email':    'jcholet@gmail.com',
	        '#password':    'admin1234',
    	}, false);

	}).thenClick('#btn-sign-in', function(){

		test.assertTitle("Easypass - Consulter le profil de jcholet");

	}).thenClick('#btn-passwords', function(){

		test.assertTitle("Easypass - Mes mots de passes", "Easypass user passwords page has correct title.");
		test.assertElementCount('li.list-group-item', 2, "The password list has correct password count.");

	}).thenClick('#btn-add-password', function(){

		test.assertTitle("Easypass - Ajout d'un mot de passe", "Easypass adding password page has correct title.");

		this.fillSelectors('#form-password', {
	        '#title':    '',
	        '#site':    '',
	        '#password':    '',
	        '#categoryId':    '',
	        '#informations':    '',
    	}, false);

	}).thenClick('#btn-save', function(){

		test.assertTitle("Easypass - Ajout d'un mot de passe", "Easypass adding password page has correct title (After incorrect submit).");
		test.assertElementCount('div.form-group.has-error', 4, "All adding password fields are validated");

		this.fillSelectors('#form-password', {
	        '#title':    'My casper password',
	        '#site':    'casper-js.fr',
	        '#password':    'casperpass',
	        '#categoryId':    2,
	        '#informations':    'This is the casperjs password',
    	}, false);

	}).thenClick('#btn-save', function(){

		casper.wait(1000, function() {

			test.assertTitle("Easypass - Mes mots de passes");
			test.assertElementCount('li.list-group-item', 3, "A password is correctly inserted.");
			test.assertEquals(
				this.evaluate(function() {
	        		return $(".password-title").eq(1).text();
	    		}),
	    	 'My casper password'
	    	);
	    	test.assertEquals(
				this.evaluate(function() {
	        		return $(".password-informations").eq(1).text().trim();
	    		}),
	    	 'This is the casperjs password'
	    	);
	    	test.assertEquals(
				this.evaluate(function() {
	        		return $(".password-field").eq(1).val();
	    		}),
	    	 'casperpass'
	    	);

			test.assertExists('img.img-password-category[src="/easypass/fichier?nom=forum1.png"]', "A category picture is displayed");

		}).thenClick('#btn-logout', function(){
		});

	});

	casper.run(function() {
    	test.done();
	});

});

casper.test.begin('Edit personnal password test', 17, function suite(test) {

    casper.start("http://localhost:8080/easypass", function() {

    	this.fillSelectors('#form-sign-in', {
	        '#email':    'adrienturcey@outlook.com',
	        '#password':    'admin1234',
    	}, false);

	}).thenClick('#btn-sign-in', function(){

		test.assertTitle("Easypass - Liste des utilisateurs");

	}).thenOpen('http://localhost:8080/easypass/utilisateur', function(){

		test.assertTitle("Easypass - Consulter le profil de aturcey");

	}).thenClick('#btn-passwords', function(){

		test.assertTitle("Easypass - Mes mots de passes", "Easypass user passwords page has correct title.");

	}).thenClick('a[href="/easypass/utilisateur/mots-de-passes/editer?passwordId=7"]', function(){

		test.assertTitle("Easypass - Edition du mot de passe Bureau Windows", "Easypass edit password page has correct title.");

		test.assertEquals(this.getElementAttribute('#title', 'value'), 'Bureau Windows');
		test.assertEquals(this.getElementAttribute('#site', 'value'), 'Aucun site');
		test.assertEquals(this.getElementAttribute('#password', 'value'), 'mdpwindows98');
		test.assertEquals(this.getElementAttribute('#categoryId option[selected]', 'value'), "5");

		test.assertEquals(
			this.evaluate(function() {
        		return $("#informations").val().trim();
	    	}),
			'Mot de passe du bureau.'
		);
		
		this.fillSelectors('#form-password', {
	        '#title':    'Bureau Windows edited',
	        '#site':    'windows.fr',
	        '#password':    'mdpwindows2000',
	        '#categoryId':    "3",
	        '#informations':    'Aucune information',
    	}, false);

	}).thenClick('#btn-save', function(){

		casper.wait(1000, function() {

			test.assertExists('div.alert.alert-success', "Casper has successfully edited a password.");
	    	test.assertSelectorHasText('div.alert.alert-success', 'Message : Le mot de passe a bien été édité.');

			test.assertTitle("Easypass - Mes mots de passes");
			test.assertElementCount('li.list-group-item', 2, "A password is correctly inserted.");
			test.assertEquals(
				this.evaluate(function() {
	        		return $(".password-title").eq(0).text();
	    		}),
	    	 'Bureau Windows edited'
	    	);
	    	test.assertEquals(
				this.evaluate(function() {
	        		return $(".password-informations").eq(0).text().trim();
	    		}),
	    	 'Aucune information'
	    	);
	    	test.assertEquals(
				this.evaluate(function() {
	        		return $(".password-field").eq(0).val();
	    		}),
	    	 'mdpwindows2000'
	    	);

			test.assertExists('img.img-password-category[src="/easypass/fichier?nom=License-unknown.png"]', "A category picture is displayed after editing a password");

		});

	}).thenClick("#btn-logout", function(){
	});

	casper.run(function() {
    	test.done();
	});

});

casper.test.begin('Delete password test', 4, function suite(test) {

    casper.start("http://localhost:8080/easypass", function() {

    	this.fillSelectors('#form-sign-in', {
	        '#email':    'adrienturcey@outlook.com',
	        '#password':    'admin1234',
    	}, false);

	}).thenClick('#btn-sign-in', function(){
	}).thenOpen('http://localhost:8080/easypass/utilisateur', function(){
	}).thenClick('#btn-passwords', function(){

		test.assertElementCount('li.list-group-item', 2, "The password list for deletion is correct.");

	}).thenClick('button#btn-delete-password', function(){

		casper.wait(1000, function() {
			test.assertExists('div.alert.alert-success', "Casper has successfully deleted a password.");
		   	test.assertSelectorHasText('div.alert.alert-success', 'Message : Le mot de passe a bien été supprimé.');
		   	test.assertElementCount('li.list-group-item', 1, "A password is correctly deleted.");
		});

	}).thenClick("#btn-logout", function(){
	});

	casper.run(function() {
    	test.done();
	});

});