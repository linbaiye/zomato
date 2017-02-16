/**
 *
 */
function BrokerService() {
	var self = this;
	self.cooperators = {};
	this.acceptCooperation = function(name, accepter) {
		self.cooperators[name] = accepter;
	}
	this.requestCooperation = function(name, data) {
		if (!self.cooperators[name]) {
			return null;
		}
		return self.cooperators[name](data);
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
