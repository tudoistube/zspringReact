var Znote = React.createClass({
	displayName: 'Znote',


	getInitialState: function () {
		return { editing: false };
	},
	componentWillMount: function () {
		this.style = {
			right: this.randomBetween(0, window.innerWidth - 150) + 'px',
			top: this.randomBetween(0, window.innerHeight - 150) + 'px',
			transform: 'rotate(' + this.randomBetween(-15, 15) + 'deg)'
		};
	},
	componentDidMount: function () {
		$(this.getDOMNode()).draggable();
	},
	randomBetween: function (p_min, p_max) {
		return p_min + Math.ceil(Math.random() * p_max);
	},
	edit: function () {
		this.setState({ editing: true });
	},
	save: function () {
		this.props.onChange(this.refs.znewText.getDOMNode().value, this.props.index);
		this.setState({ editing: false });
	},
	remove: function () {
		this.props.onRemove(this.props.index);
	},
	renderDisplay: function () {
		return React.createElement(
			'div',
			{ className: 'znote', style: this.style },
			React.createElement(
				'p',
				null,
				this.props.children
			),
			React.createElement(
				'span',
				null,
				React.createElement('button', { onClick: this.edit,
					className: 'btn btn-primary glyphicon glyphicon-pencil' }),
				React.createElement('button', { onClick: this.remove,
					className: 'btn btn-danger glyphicon glyphicon-trash' })
			)
		);
	},
	renderForm: function () {
		return React.createElement(
			'div',
			{ className: 'znote', style: this.style },
			React.createElement('textarea', { ref: 'znewText', defaultValue: this.props.children,
				className: 'form-control' }),
			React.createElement('button', { onClick: this.save,
				className: 'btn btn-success btn-sm glyphicon glyphicon-floppy-disk' })
		);
	},
	render: function () {
		if (this.state.editing) {
			return this.renderForm();
		} else {
			return this.renderDisplay();
		}
	}
});

var Zboard = React.createClass({
	displayName: 'Zboard',

	propTypes: {
		count: function (props, propName) {
			if (typeof props[propName] !== "number") {
				return new Error('The count property must be a number.');
			}
			if (props[propName] > 100) {
				return new Error("Creating " + props[propName] + "notes is ridiculous...");
			}
		}
	},
	getInitialState: function () {
		return {
			znotes: []
		};
	},
	nextId: function () {
		this.uniqueId = this.uniqueId || 0;
		return this.uniqueId++;
	},
	componentWillMount: function () {
		var zself = this;
		if (this.props.count) {
			$.getJSON("http://baconipsum.com/api/?type=all-meat&sentences=" + this.props.count + "&start-with-lorem=1&callback=?", function (results) {
				results[0].split('. ').forEach(function (sentence) {
					zself.add(sentence.substring(0, 40));
				});
			});
		}
	},
	add: function (p_text) {
		var zarr = this.state.znotes;
		zarr.push({
			id: this.nextId(),
			znote: p_text
		});
		this.setState({ znotes: zarr });
	},
	update: function (p_text, i) {
		var zarr = this.state.znotes;
		zarr[i].znote = p_text;
		this.setState({ znotes: zarr });
	},
	remove: function (i) {
		var zarr = this.state.znotes;
		zarr.splice(i, 1);
		this.setState({ znotes: zarr });
	},
	eachNote: function (p_note, i) {
		return React.createElement(
			Znote,
			{ key: p_note.id, index: i,
				onChange: this.update,
				onRemove: this.remove },
			p_note.znote
		);
	},
	render: function () {
		return React.createElement(
			'div',
			{ className: 'zboard' },
			this.state.znotes.map(this.eachNote),
			React.createElement('button', { className: 'btn btn-sm btn-success glyphicon glyphicon-plus',
				onClick: this.add.bind(null, "New Note") })
		);
	}

});

React.render(React.createElement(Zboard, { count: 12 }), document.getElementById('zreact-container'));