/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.tts.server.handler.user;

import java.util.Map;
import java.util.logging.Logger;

import com.app.tts.services.UserService;
import com.app.tts.session.redis.SessionStore;
import com.app.tts.util.AppParams;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.http.HttpServerRequest;
import io.vertx.rxjava.ext.web.RoutingContext;

public class GetUserHandler implements Handler<RoutingContext>, SessionStore {

	@Override
	public void handle(RoutingContext routingContext) {

		routingContext.vertx().executeBlocking(future -> {
			try {
				HttpServerRequest httpServerRequest = routingContext.request();
				String userId = httpServerRequest.getParam("userId");
				LOGGER.info("---userId = "+ userId);
				JsonObject data = new JsonObject();

//				String sessionId = cookie.getValue();
//				Users loggedInUser = gson.fromJson(jedis.get(sessionId), Users.class);
				Map user = UserService.getUserById(userId);
				
//				if (!user.isEmpty()) {
//					data = new JsonObject(user.toString());
//				}
				
				routingContext.put(AppParams.RESPONSE_CODE, HttpResponseStatus.OK.code());
				routingContext.put(AppParams.RESPONSE_MSG, HttpResponseStatus.OK.reasonPhrase());
				routingContext.put(AppParams.RESPONSE_DATA, user);
				future.complete();
			} catch (Exception e) {
				routingContext.fail(e);
			}
		}, asyncResult -> {
			if (asyncResult.succeeded()) {
				routingContext.next();
			} else {
				routingContext.fail(asyncResult.cause());
			}
		});
	}

	private static final Logger LOGGER = Logger.getLogger(GetUserHandler.class.getName());
	
}
