<template>
	<li class="preview-item">
		<span>{{ id }}</span>
		<span>{{ name }}</span>
	</li>
</template>

<script>
export default {
	name: "PreviewItem",
	props: [{'id', 'name'}]
}
</script>