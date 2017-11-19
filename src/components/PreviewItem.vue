<template>
	<li class="preview-item">
		<span>{{ id }}</span>
		<span>{{ name }}</span>
	</li>
</template>

<script>
export default {
	name: "previewitem",
	props: [{'id', 'name'}]
}
</script>