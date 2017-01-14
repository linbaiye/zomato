/**
 * 
 */
function BrokerService() {
	var cachedMessages = {};
	function putSingleMessage(receiver, message) {
		if (!receiver || !message) {
			return;
		}
		cachedMessages[receiver] = message;
	}
	
	function getSingleMessage(receiver) {
		return cachedMessages[receiver];
	}

	return {
		putSingleMessage: putSingleMessage,
		getSingleMessage: getSingleMessage
	}
}

function shortenFunction(maxLength) {
	return function(description) {
		if (description && description.length > maxLength) {
			return description.substr(0, maxLength-3) + "...";
		}
		return description;
	}
}